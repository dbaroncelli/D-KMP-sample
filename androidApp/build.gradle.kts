import org.jetbrains.compose.compose

group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version Versions.mppCompose
}

dependencies {
    implementation(project(":composables"))
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.3.0-beta02")
    implementation("androidx.lifecycle:lifecycle-process:2.3.1")
    implementation("androidx.appcompat:appcompat:1.4.0-alpha02")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation(compose.ui)
    implementation(compose("org.jetbrains.compose.ui:ui-tooling"))
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.materialIconsExtended)
    implementation(compose.runtime)

}

android {
    compileSdk = Versions.compile_sdk
    buildToolsVersion = Versions.build_tools

    defaultConfig {
        applicationId = "eu.baroncelli.dkmpsample"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // packagingOptions {
    //    exclude("META-INF/*.kotlin_module")
    //}
    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = true
            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true
            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files. */
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        compose = true
    }
}

kotlin.sourceSets.all {
    languageSettings.apply {
        useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}
