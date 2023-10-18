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
    namespace = "com.teamwiney.data"
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
    implementation(project(":core:common"))

    with(Dependency) {
        kapt(HILT_ANDROID_COMPILER)
        implementation(COROUTINES_ANDROID)
        implementation(COMPOSE_PAGING)
        implementation(HILT_ANDROID)
        implementation(DATASTORE)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        implementation(RETROFIT)
        implementation(CONVERTER_GSON)
        implementation(OKHTTP)
        implementation(LOGGING_INTERCEPTOR)
        debugImplementation(CHUCKER_LIBRARY)
        testImplementation(JUNIT)
    }
}