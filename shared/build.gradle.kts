import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.skie)
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar.jdk)
}

kotlin {
    jvmToolchain(18)
    androidTarget()
    jvm("desktop")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            binaryOption("bundleId", "eu.baroncelli.dkmpsample.shared")
        }
    }
    sourceSets {

        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.multiplatformSettings)
            implementation(libs.sqlDelight.common)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.multiplatformSettings.test)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.android)
            implementation(libs.sqlDelight.android)
            implementation(libs.slf4j)
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.sqlDelight.jvm)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.jvm)
                implementation(libs.ktor.jvm)
                implementation(libs.sqlDelight.jvm)
                implementation(libs.logback)
            }
        }
        // val desktopTest by getting { dependencies { }}
        iosMain.dependencies {
            implementation(libs.ktor.ios)
            implementation(libs.sqlDelight.ios)
        }
        // iosTest.dependencies {}
    }
}

android {
    namespace = "eu.baroncelli.dkmpsample.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.compiler.get()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}

sqldelight {
    databases {
        create("LocalDb") {
            packageName.set("mylocal.db")
            srcDirs("src/commonMain/kotlin")
        }
    }
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xexpect-actual-classes"
    }
}