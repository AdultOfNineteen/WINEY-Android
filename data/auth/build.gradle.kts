plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(KOTLIN_KAPT)
    }
}

android {
    namespace = "com.teamwiney.data.auth"
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
}

dependencies {
    implementation(project(":core:network"))

    with (Dependency) {
        kapt(HILT_ANDROID_COMPILER)
        implementation(COROUTINES_ANDROID)
        implementation(HILT_ANDROID)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        testImplementation(JUNIT)
    }
}