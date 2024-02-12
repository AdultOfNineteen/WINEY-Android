import java.util.Properties

plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(KOTLIN_KAPT)
    }
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.teamwiney.feature.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "GOOGLE_OAUTH_CLIENT_ID",
            properties["google.oauth.client.id"] as String
        )
        buildConfigField(
            "String",
            "GOOGLE_OAUTH_CLIENT_SECRET",
            properties["google.oauth.client.secret"] as String
        )
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
        buildConfig = true
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
        implementation(COMPOSE_MATERIAL)
        implementation(COMPOSE_UI)
        implementation(COMPOSE_UI_TOOLING)
        implementation(COMPOSE_UI_PREVIEW)
        implementation(NAVIGATION_COMPOSE)
        implementation(HILT_ANDROID)
        implementation(HILT_NAVIGATION_COMPOSE)
        implementation(LIFECYCLE_RUNTIME_COMPOSE)
        implementation(DATASTORE)
        implementation(KAKAO_SDK_USER) // 카카오 로그인
        implementation(PLAY_SERVICES_AUTH) // 구글 로그인
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        androidTestImplementation(COMPOSE_UI_TEST_JUNIT4)
        testImplementation(JUNIT)
    }
}