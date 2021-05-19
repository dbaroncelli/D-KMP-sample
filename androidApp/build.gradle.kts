group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.3.0-alpha07")
    implementation("androidx.lifecycle:lifecycle-process:2.3.1")
    implementation("androidx.appcompat:appcompat:1.3.0-rc01")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-graphics:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    implementation("androidx.compose.material:material:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
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
            /* isShrinkResources = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

kotlin.sourceSets.all {
    languageSettings.apply {
        useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
        useExperimentalAnnotation("androidx.compose.foundation.ExperimentalFoundationApi")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xskip-prerelease-check", "-Xskip-metadata-version-check")
    }
}