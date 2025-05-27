plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.sonarqube)
}

android {
    namespace = "com.irurueta.android.recyclerviewmanager"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testOptions.targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val libraryVersion = "1.0.0"
        val buildNumber = System.getenv("BUILD_NUMBER")
        val apkPrefixLabels = listOf("android-recycler-view", libraryVersion, buildNumber)
        base.archivesName = apkPrefixLabels.filter({ it != "" }) .joinToString("-")
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE-notice.md")
            excludes.add("META-INF/LICENSE.md")
        }
    }
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.withType<Test>() {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            val sysProps = System.getProperties()
            if (sysProps["idea.platform.prefix"] != null) {
                // Built from AndroidStudio
                // When running from Android Studio, there is no need to generate jacoco report
                // and nothing is changed so that debug variant of app can still run
            } else {
                // Built from command line
                // Enabling this allows correct jacoco report generation for instrumentation tests
                // in CI server, however prevents execution of debug variant of demo app in Android
                // Studio
                if ("org.jacoco" == this.requested.group) {
                    this.useVersion("0.8.13")
                }
            }
        }
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "albertoirurueta_android-recycler-view")
        property("sonar.projectName", "android-recycler-view-${project.name}")
        property("sonar.organization", "albertoirurueta-github")
        property("sonar.host.url", "https://sonarcloud.io")

        property("sonar.tests", listOf("src/test/java", "src/androidTest/java"))
        property("sonar.test.inclusions",
            listOf("**/*Test*/**", "src/androidTest/**", "src/test/**"))
        property("sonar.test.exclusions",
            listOf("**/*Test*/**", "src/androidTest/**", "src/test/**"))
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "src/main/java")
        property("sonar.exclusions", "**/*Test*/**,*.json,'**/*test*/**,**/.gradle/**,**/R.class")

        val libraries = project.android.sdkDirectory.path + "/platforms/android-36/android.jar"
        property("sonar.libraries", libraries)
        property("sonar.java.libraries", libraries)
        property("sonar.java.test.libraries", libraries)
        property("sonar.binaries", "build/intermediates/javac/debug/classes,build/tmp/kotlin-classes/debug")
        property("sonar.java.binaries", "build/intermediates/javac/debug/classes,build/tmp/kotlin-classes/debug")

        property("sonar.coverage.jacoco.xmlReportPaths",
            listOf("${project.layout.buildDirectory}/reports/coverage/androidTest/debug/report.xml",
                "${project.layout.buildDirectory}/reports/coverage/test/report.xml"))
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.junit.reportsPath", "build/test-results/testDebugUnitTest, build/outputs/androidTest-results/connected")
        property("sonar.android.lint.report", "build/reports/lint-results-debug.xml")
    }
}

// Lines to add in module level build.gradle file for modules you publish

ext {
    // Provide your own coordinates here
    set("PUBLISH_GROUP_ID", "com.irurueta")
    // line below should have the same value as libraryVersion
    set("PUBLISH_VERSION", "1.0.0")
    set("PUBLISH_ARTIFACT_ID", "irurueta-android-recycler-view")
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.hermes)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
}