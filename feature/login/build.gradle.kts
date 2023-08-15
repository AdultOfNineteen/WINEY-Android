plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(KOTLIN_KAPT)
    }
}

android {
    namespace = "com.teamwiney.feature.login"
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
    implementation(project(":core:common"))
    implementation(project(":core:design"))
    implementation(project(":data"))

    with(Dependency) {
        kapt(HILT_ANDROID_COMPILER)
        implementation(ANDROID_CORE_KTX)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
        implementation(COMPOSE_UI_TOOLING)
        implementation(COMPOSE_UI_PREVIEW)
        implementation(NAVIGATION_COMPOSE)
        implementation(DAGGER_COMPILER)
        implementation(HILT_NAVIGATION_COMPOSE)
        implementation(HILT_ANDROID)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        androidTestImplementation(COMPOSE_UI_TEST_JUNIT4)
        testImplementation(JUNIT)
        implementation("com.kakao.sdk:v2-user:2.15.0") // 카카오 로그인
    }
}