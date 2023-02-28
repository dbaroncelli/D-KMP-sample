group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":composables"))
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.7.0-alpha04")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")
    implementation(compose.runtime)
}

android {
    compileSdk = extra["android.compileSdk"].toString().toInt()
    defaultConfig {
        applicationId = "eu.baroncelli.dkmpsample"
        minSdk = extra["android.minSdk"].toString().toInt()
        targetSdk = extra["android.targetSdk"].toString().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    namespace = "eu.baroncelli.dkmpsample"
}