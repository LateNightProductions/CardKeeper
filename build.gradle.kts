plugins {
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
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