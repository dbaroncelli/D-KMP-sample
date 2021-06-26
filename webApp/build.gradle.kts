import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.mppCompose
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://www.jetbrains.com/intellij-repository/releases")
    maven("https://jetbrains.bintray.com/intellij-third-party-dependencies")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.web.widgets)
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(npm("copy-webpack-plugin", "9.0.0"))
                implementation(npm("@material-ui/icons", "4.11.2"))
            }
        }
        val jsTest by getting
    }
}