plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)


    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "io.github.romantsisyk.wear"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.romantsisyk.wear"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation (project(":common"))
    // Wear Compose
    implementation (libs.androidx.compose.material.v140)

    // Tiles and Complications
    implementation (libs.androidx.tiles.v141)
    implementation (libs.androidx.watchface.complications.data.source)
    implementation (libs.androidx.compose.material.v140)
    implementation (libs.androidx.compose.foundation.v140)
    implementation (libs.androidx.compose.navigation)

    implementation(libs.androidx.compose.material.v120)
    implementation(libs.androidx.lifecycle.runtime.ktx.v260)
    implementation(libs.hilt.android.v252)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.kotlinx.serialization.json)
    implementation(libs.play.services.wearable)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.tiles)
    implementation(libs.androidx.tiles.material)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.androidx.watchface.complications.data.source.ktx)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}