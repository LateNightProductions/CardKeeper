buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.1")
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