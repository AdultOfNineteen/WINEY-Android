import java.util.Properties

plugins {
    with(Plugins) {
        id(ANDROID_APPLICATION)
        id(JETBRAINS_KOTLIN_ANDROID)
        id(DAGGER_HILT_ANDROID)
        id(KOTLIN_KAPT)
    }
    id("com.google.gms.google-services")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
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

        signingConfig = signingConfigs.getByName("debug")

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties["kakao.native.app.key"] as String
        )
        resValue(
            "string",
            "KAKAO_NATIVE_APP_KEY_FULL",
            properties["kakao.native.app.key.full"] as String
        )
        resValue(
            "string",
            "NAVER_CLIENT_ID",
            properties["naver.client.id"] as String
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = properties["SIGNED_KEY_ALIAS"] as String?
            keyPassword = properties["SIGNED_KEY_PASSWORD"] as String?
            storeFile = properties["SIGNED_STORE_FILE"]?.let { file(it) }
            storePassword = properties["SIGNED_STORE_PASSWORD"] as String?
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
    implementation(project(":core:common"))
    implementation(project(":data"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:map"))
    implementation(project(":feature:note"))
    implementation(project(":feature:mypage"))
    implementation("com.kakao.sdk:v2-user:2.15.0") // 카카오 로그인
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    
    with(Dependency) {
        implementation(ANDROID_CORE_KTX)
        implementation(APPCOMPAT)
        implementation(ACTIVITY_COMPOSE)
        implementation(NAVIGATION_COMPOSE)
        implementation(COMPOSE_BOM)
        implementation(COMPOSE_MATERIAL)
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_UI)
        implementation(LIFECYCLE_RUNTIME_KTX)
        implementation(LIFECYCLE_RUNTIME_COMPOSE)
        implementation(COROUTINES_ANDROID)
        implementation(KOTLIN_BOM)
        implementation(COMPOSE_BOM)

        implementation(platform(FIREBASE_BOM))
        implementation(FIREBASE_ANALYTICS)
        implementation(FIREBASE_MESSAGING)

        implementation(HILT_NAVIGATION_COMPOSE)
        implementation(HILT_ANDROID)
        implementation(DAGGER)
        implementation(DATASTORE)
        kapt(DAGGER_COMPILER)
        kapt(HILT_ANDROID_COMPILER)
        androidTestImplementation(COMPOSE_UI_TEST_JUNIT4)
        androidTestImplementation(TEST_EXT_JUNIT)
        androidTestImplementation(TEST_ESPRESSO_CORE)
        androidTestImplementation(COMPOSE_BOM)

        testImplementation(JUNIT)

        debugImplementation(COMPOSE_UI_TOOLING)
        debugImplementation(COMPOSE_UI_TEST_MANIFEST)
    }
}