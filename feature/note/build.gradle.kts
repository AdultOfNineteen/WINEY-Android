plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(KOTLIN_KAPT)
    }
}

android {
    namespace = "com.teamwiney.feature.note"
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

    with(Dependency) {
        kapt(HILT_ANDROID_COMPILER)
        implementation(ANDROID_CORE_KTX)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
        implementation(COMPOSE_PAGING)
        implementation(COMPOSE_PAGING_RUNTIME)
        implementation(COMPOSE_UI_TOOLING)
        implementation(COMPOSE_UI_PREVIEW)
        implementation(NAVIGATION_COMPOSE)
        implementation(HILT_ANDROID)
        implementation(HILT_NAVIGATION_COMPOSE)
        implementation(LIFECYCLE_RUNTIME_COMPOSE)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        androidTestImplementation(COMPOSE_UI_TEST_JUNIT4)
        testImplementation(JUNIT)
    }
}