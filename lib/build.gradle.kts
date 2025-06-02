import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.sonarqube)
    id("com.vanniktech.maven.publish") version "0.32.0"
}

val libraryVersion = "1.0.2"

android {
    namespace = "com.irurueta.android.recyclerviewmanager"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testOptions.targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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

sonar {
    properties {
        property("sonar.scanner.skipJreProvisioning", true)
        property("sonar.projectKey", "albertoirurueta_irurueta-android-recycler-view-manager")
        property("sonar.projectName", "android-recycler-view-manager")
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
            listOf("build/reports/coverage/androidTest/debug/connected/report.xml",
                "build/reports/coverage/test/report.xml"))
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.junit.reportsPath",
            listOf("build/build/test-results/testDebugUnitTest",
                "build/build/outputs/androidTest-results/connected/debug"))
        property("sonar.android.lint.report", "build/build/reports/lint-results-debug.xml")
    }
}

dependencies {
    implementation(libs.material)
    api(libs.hermes)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.mockk.android)
}

mavenPublishing {
    configure(AndroidSingleVariantLibrary(
        // the published variant
        variant = "release",
        // whether to publish a sources jar
        sourcesJar = true,
        // whether to publish a javadoc jar
        publishJavadocJar = true,
    ))

    publishToMavenCentral(SonatypeHost.DEFAULT)
    // or when publishing to https://s01.oss.sonatype.org
    //publishToMavenCentral(SonatypeHost.S01)
    // or when publishing to https://central.sonatype.com/
    //publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates("com.irurueta", "irurueta-android-recycler-view-manager", libraryVersion)

    pom {
        name.set("irurueta-android-recycler-view-manager")
        description.set("Recycler view utility to simplify adapter notifications when collections of data are modified")
        inceptionYear.set("2025")
        url.set("https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/")
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/blob/main/LICENSE")
                distribution.set("https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/blob/main/LICENSE")
            }
        }
        developers {
            developer {
                id.set("albertoirurueta")
                name.set("Alberto Irurueta")
                email.set("alberto@irurueta.com")
                url.set("https://github.com/albertoirurueta/")
            }
        }
        scm {
            url.set("https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/")
            connection.set("scm:git:github.com/albertoirurueta/irurueta-android-recycler-view-manager.git")
            developerConnection.set("scm:git:ssh://github.com/albertoirurueta/irurueta-android-recycler-view-manager.git")
        }
    }
}
