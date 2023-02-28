group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    android {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                api(compose.foundation)
                api(compose.ui)
                api(compose.uiTooling)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.7.0-alpha04")
            }
        }
    }
}

android {
    namespace = "eu.baroncelli.dkmpsample.composables"
    compileSdk = extra["android.compileSdk"].toString().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    defaultConfig {
        minSdk = extra["android.minSdk"].toString().toInt()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}
