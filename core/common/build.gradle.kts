import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
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

    implementation(libs.naver.map.compose)
    implementation(libs.android.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material)
    implementation(libs.coroutines.android)
    implementation(libs.lifecycle.runtime.viewmodel)
    implementation(libs.converter.gson)
    implementation(libs.datastore)
}