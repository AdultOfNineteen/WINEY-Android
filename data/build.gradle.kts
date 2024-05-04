import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
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

    kapt(libs.hilt.android.compiler)
    implementation(libs.coroutines.android)
    implementation(libs.hilt.android)
    implementation(libs.datastore)
    implementation(libs.compose.paging)
    implementation(libs.bundles.network)
    implementation(libs.local.broadcast.manager)
}