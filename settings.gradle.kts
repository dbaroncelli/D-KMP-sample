pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        kotlin("plugin.serialization").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("android").version(extra["kotlin.version"] as String)
        id("com.android.application").version(extra["android.gradlePlugin"] as String)
        id("com.android.library").version(extra["android.gradlePlugin"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("com.squareup.sqldelight").version(extra["sqlDelight.version"] as String)
    }
}

rootProject.name = "D-KMP-sample"

include(":androidApp")
include(":desktopApp")
include(":composables")
include(":shared")