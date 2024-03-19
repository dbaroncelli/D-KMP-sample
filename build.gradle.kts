group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.skie) apply false
}