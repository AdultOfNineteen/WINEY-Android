@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.tools.build.gradle.plugin)
    }
}

// 모든 하위 프로젝트/모듈에 공통적인 구성 옵션을 추가하는 최상위 빌드
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

