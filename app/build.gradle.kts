plugins {
    with(Plugins) {
        id(ANDROID_APPLICATION)
        id(JETBRAINS_KOTLIN_ANDROID)
    }
}

android {
    namespace = "com.teamwiney.winey"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.teamwiney.winey"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:login"))
    implementation(project(":feature:home"))
    implementation(project(":feature:createNote"))
    implementation(project(":feature:noteCollection"))
    implementation(project(":feature:mypage"))

    with(Dependency) {
        implementation(ANDROID_CORE_KTX)
        implementation(APPCOMPAT)
        implementation(ACTIVITY_COMPOSE)
        implementation(NAVIGATION_COMPOSE)
        implementation(COMPOSE_BOM)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
        implementation(LIFECYCLE_RUNTIME_KTX)
        implementation(LIFECYCLE_RUNTIME_COMPOSE)
        implementation(COROUTINES_ANDROID)
        implementation(KOTLIN_BOM)
        implementation(COMPOSE_BOM)

        implementation(HILT_ANDROID)

        androidTestImplementation(COMPOSE_UI_TEST_JUNIT4)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        androidTestImplementation(COMPOSE_BOM)

        testImplementation(JUNIT)

        debugImplementation(COMPOSE_UI_TOOLING)
        debugImplementation(COMPOSE_UI_TEST_MANIFEST)
    }
}