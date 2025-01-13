plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.awscherb.cardkeeper.create"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":code-ui-common"))
    implementation(project(":compose-common"))
    implementation(project(":data:core"))
    implementation(project(":data:barcode"))
    implementation(project(":data:types"))

    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.navigation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.zxing.core)
    implementation(libs.dagger)
    implementation(libs.dagger.hilt)

    ksp(libs.dagger.compiler)
    ksp(libs.dagger.hilt.compiler)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}