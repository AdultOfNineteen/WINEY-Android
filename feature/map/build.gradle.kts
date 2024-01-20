plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(KOTLIN_KAPT)
        id(GOOGLE_SERVICES)
        id(FIREBASE_CRASHLYTICS)
    }
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

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("io.github.fornewid:naver-map-compose:1.3.3")
    implementation("io.github.fornewid:naver-map-location:16.0.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    with(Dependency) {
        kapt(HILT_ANDROID_COMPILER)
        implementation(COMPOSE_GLIDE)
        implementation(ANDROID_CORE_KTX)
        implementation(COMPOSE_MATERIAL)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
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