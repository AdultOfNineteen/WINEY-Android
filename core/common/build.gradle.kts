import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.dagger.hilt.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
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
            "AMPLITUDE_API_KEY",
            properties["amplitude.api.key"] as String
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", properties["prod.base.url"] as String)
        }

        getByName("debug") {
            buildConfigField("String", "BASE_URL", properties["dev.base.url"] as String)
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
    implementation(libs.naver.map.compose)
    implementation(libs.android.core.ktx)
    implementation(libs.bundles.compose.ui)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material)
    implementation(libs.coroutines.android)
    implementation(libs.lifecycle.runtime.viewmodel)
    implementation(libs.converter.gson)

    implementation(libs.amplitude)
    implementation(libs.datastore)
    implementation(libs.dagger)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}