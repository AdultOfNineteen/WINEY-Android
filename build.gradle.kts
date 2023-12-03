buildscript {
    dependencies {
        classpath(Dependency.HILT_ANDROID_GRADLE_PLUGIN)
        classpath(Dependency.KOTLIN_GRADLE_PLUGIN)
    }
}
// 모든 하위 프로젝트/모듈에 공통적인 구성 옵션을 추가하는 최상위 빌드
plugins {
    with(Plugins) {
        id(ANDROID_APPLICATION) version Versions.AGP apply false
        id(ANDROID_LIBRARY) version Versions.AGP apply false
        id(JETBRAINS_KOTLIN_ANDROID) version Versions.KOTLIN apply false
        id(DAGGER_HILT_PLUGIN) version Versions.HILT apply false
    }
    id("com.google.gms.google-services") version "4.4.0" apply false
}