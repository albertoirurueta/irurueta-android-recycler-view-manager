// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.dokka) apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

apply(from = "${rootProject.projectDir}/scripts/publish-root.gradle")

project.delete {
    delete(rootProject.layout.buildDirectory)
}
