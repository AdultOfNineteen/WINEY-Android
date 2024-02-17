@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.google.services.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
}

android {
    namespace = "com.teamwiney.feature.map"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:common"))
    implementation(project(":data"))

    kapt(libs.hilt.android.compiler)
    implementation(libs.accompanist.systemui)
    implementation(libs.play.services.auth)
    implementation(libs.bundles.map)
    implementation(libs.android.core.ktx)
    implementation(libs.compose.coil)
    implementation(libs.bundles.compose.ui)
    implementation(libs.bundles.hilt.navigation)
    implementation(libs.lifecycle.runtime.compose)
}