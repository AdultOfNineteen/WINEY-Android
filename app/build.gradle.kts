import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.dagger.hilt.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.google.services.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
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
        versionCode = 5
        versionName = "1.0.1"

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
    implementation(project(":data"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:map"))
    implementation(project(":feature:note"))
    implementation(project(":feature:mypage"))

    implementation(libs.android.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.coroutines.android)
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kakao.sdk.user)
    implementation(libs.play.services.auth)
    implementation(libs.naver.map.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaing)

    implementation(libs.bundles.hilt.navigation)
    implementation(libs.dagger)
    implementation(libs.datastore)
    kapt(libs.hilt.android.compiler)
}