import java.util.Properties

plugins {
    with(Plugins) {
        id(ANDROID_LIBRARY)
        id(JETBRAINS_KOTLIN_ANDROID)
    }
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.teamwiney.core.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField(
            "String",
            "BASE_URL",
            properties["base.url"] as String
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
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
}

dependencies {
    implementation(project(":core:design"))

    with(Dependency) {
        implementation(NAVER_MAP_COMPOSE)
        implementation(ANDROID_CORE_KTX)
        implementation(COMPOSE_UI)
        implementation(NAVIGATION_COMPOSE)
        implementation(COMPOSE_MATERIAL)
        implementation(COROUTINES_ANDROID)
        implementation(LIFECYCLE_RUNTIME_VIEWMODEL)
        implementation(CONVERTER_GSON)
        implementation(DATASTORE)
    }
}