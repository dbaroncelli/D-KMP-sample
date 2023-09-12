group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight")
    id("co.touchlab.skie")
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
}

kotlin {
    jvmToolchain(18)
    androidTarget()
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
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-client-core:"+extra["ktor.version"])
                implementation("io.ktor:ktor-client-logging:"+extra["ktor.version"])
                implementation("io.ktor:ktor-client-content-negotiation:"+extra["ktor.version"])
                implementation("io.ktor:ktor-serialization-kotlinx-json:"+extra["ktor.version"])
                implementation("com.russhwolf:multiplatform-settings-no-arg:"+extra["multiplatformSettings.version"])
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.russhwolf:multiplatform-settings-test:"+extra["multiplatformSettings.version"])
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:"+extra["ktor.version"])
                implementation("app.cash.sqldelight:android-driver:"+extra["sqlDelight.version"])
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("app.cash.sqldelight:sqlite-driver:"+extra["sqlDelight.version"])
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
                implementation("io.ktor:ktor-client-apache:"+extra["ktor.version"])
                implementation("app.cash.sqldelight:sqlite-driver:"+extra["sqlDelight.version"])
                implementation("ch.qos.logback:logback-classic:1.4.7")
            }
        }
        val desktopTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:"+extra["ktor.version"])
                implementation("app.cash.sqldelight:native-driver:"+extra["sqlDelight.version"])
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "eu.baroncelli.dkmpsample.shared"
    compileSdk = extra["android.compileSdk"].toString().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = extra["android.minSdk"].toString().toInt()
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = extra["android.composeCompiler"].toString()
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