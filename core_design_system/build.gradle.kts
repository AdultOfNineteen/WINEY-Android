plugins {
    with(Plugins) {
        id(ANDROID_APPLICATION)
        id(JETBRAINS_KOTLIN_ANDROID)
    }
}

android {
    namespace = "com.teamwiney.core_design_system"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.teamwiney.core_design_system"
        minSdk = 24
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }

}

dependencies {

    with(Dependency) {
        implementation(ANDROID_CORE_KTX)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
        implementation(COMPOSE_UI_TOOLING)
        implementation(COMPOSE_UI_PREVIEW)
    }

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}