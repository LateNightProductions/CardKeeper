import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.awscherb.cardkeeper"
        minSdk = 28
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 92
        versionName = "1.1"
    }

    flavorDimensions += "env"

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationId = "com.awscherb.cardkeeper.dev"
            buildConfigField("Boolean", "PRODUCTION", "false")
        }
        create("prod") {
            dimension = "env"
            applicationId = "com.awscherb.cardkeeper"
            buildConfigField("Boolean", "PRODUCTION", "true")
        }
    }

    buildTypes {
        val properties = Properties()
        properties.load(FileInputStream(project.file("clrvynce.properties")))
        val cvyKey = properties.getProperty("API_KEY")
        getByName("release") {
            resValue("string", "clrvynceKey", cvyKey)
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            resValue("string", "clrvynceKey", cvyKey)
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }


    namespace = "com.awscherb.cardkeeper"

}

dependencies {

    // Support libs
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")
    kapt("androidx.lifecycle:lifecycle-common-java8:2.5.1")

    // Scanning
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.journeyapps:zxing-android-embedded:3.6.0@aar")

    // Dependency Injection
    implementation("com.google.dagger:dagger:2.43.2")
    kapt("com.google.dagger:dagger-compiler:2.43.2")

    implementation("com.clrvynce.android:sdk:1.0.2")

    // Dependencies for local unit tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.7.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    debugImplementation("androidx.fragment:fragment-testing:1.5.3")

}