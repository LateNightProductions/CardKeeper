plugins {
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" apply false
}

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.oss.licenses.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url = uri("https://maven.google.com") }
        mavenLocal()
        google()
    }
}