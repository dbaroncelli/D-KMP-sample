import org.jetbrains.compose.compose

group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.mppCompose
    id("com.android.library")
}



kotlin {
    android ()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("androidx.compose.ui.ExperimentalComposeUiApi")
                useExperimentalAnnotation("androidx.compose.foundation.ExperimentalFoundationApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose("org.jetbrains.compose.ui:ui-tooling"))
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.3.0-beta02")
            }
        }
    }
}

android {
    compileSdk = Versions.compile_sdk
    buildToolsVersion = Versions.build_tools
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}