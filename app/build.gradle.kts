plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.awscherb.cardkeeper"
        minSdk = 24
        targetSdk = 34
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
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    namespace = "com.awscherb.cardkeeper"

}

dependencies {

    implementation(project(":data:core"))
    implementation(project(":data:common"))
    implementation(project(":data:barcode"))
    implementation(project(":data:pkpass"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.coil)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.constraint)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    implementation(libs.dagger.hilt)
    implementation(libs.flexbox)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.hilt.navigation)
    implementation(libs.material)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.zxing.android.embedded)
    implementation(libs.zxing.core)
    debugImplementation(libs.compose.ui.tooling)

    kapt(libs.androidx.room.compiler)
    kapt(libs.dagger.android.processor)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.glide.compiler)


}