plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
    }
}

android {
    namespace = "com.teamwiney.core.design"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        implementation(ACTIVITY_COMPOSE)
        implementation(LIFECYCLE_RUNTIME_VIEWMODEL_COMPOSE)
        implementation(COMPOSE_COIL)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_MATERIAL)
        implementation(COMPOSE_UI)
        implementation(COMPOSE_UI_TOOLING)
        implementation(COMPOSE_UI_TEST_MANIFEST)
        implementation(COMPOSE_UI_PREVIEW)
        implementation(platform(KOTLIN_BOM))
    }
}