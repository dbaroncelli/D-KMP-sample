group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("plugin.serialization") apply false
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
    id("app.cash.sqldelight") apply false
    id("co.touchlab.skie") apply false
}