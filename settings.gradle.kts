pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "D-KMP-sample"

include(":androidApp")
include(":desktopApp")
include(":composables")
include(":webApp")
include(":shared")