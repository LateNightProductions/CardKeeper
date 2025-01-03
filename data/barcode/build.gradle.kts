plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.awscherb.cardkeeper.barcode"
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
    ksp {
        arg("room.schemaLocation", "$projectDir/barcode-schemas")
    }
}

dependencies {

    implementation(project(":data:core"))
    implementation(project(":data:common"))
    implementation(project(":data:types"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.dagger)
    implementation(libs.gson)
    implementation(libs.material)
    implementation(libs.zxing.core)

    ksp(libs.androidx.room.compiler)
    ksp(libs.dagger.compiler)
}