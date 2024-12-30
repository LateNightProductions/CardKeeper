plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.devtools.ksp)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.awscherb.cardkeeper"
        minSdk = 26
        targetSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 246
        versionName = "2.4"
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

    namespace = "com.awscherb.cardkeeper"

}

dependencies {

    implementation(project(":code-ui-common"))
    implementation(project(":compose-common"))
    implementation(project(":data:core"))
    implementation(project(":data:common"))
    implementation(project(":data:barcode"))
    implementation(project(":data:pkpass"))
    implementation(project(":data:types"))
    implementation(project(":items"))
    implementation(project(":pass-ui-common"))

    // Copy icon source
//    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    implementation(platform(libs.compose.bom))
    implementation(platform(libs.firebase.bom))
    implementation(libs.accompanist.permissions)
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
    implementation(libs.compose.constraint)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.dagger)
    implementation(libs.dagger.hilt)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
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

    ksp(libs.androidx.room.compiler)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.glide.compiler)


}