plugins {
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false

}

buildscript {
    repositories {
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