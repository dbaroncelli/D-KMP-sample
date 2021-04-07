group = "eu.baroncelli.dkmpsample"
version = "1.0-SNAPSHOT"



buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sql_delight}")
    }
}

allprojects {
    repositories {
        gradlePluginPortal()
        google()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        mavenCentral()
    }
}