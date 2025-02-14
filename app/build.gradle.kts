import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}
val kakao_login_native_app_key: String = localProperties.getProperty("kakao_login_native_app_key") ?: "kakao_login_native_app_key"

android {
    namespace = "com.example.apipracticeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apipracticeapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "NATIVE_APP_KEY", "\"$kakao_login_native_app_key\"")
        }
        debug {
            buildConfigField("String", "NATIVE_APP_KEY", "\"$kakao_login_native_app_key\"")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.datastore)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.serialization)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation ("com.kakao.sdk:v2-all:2.20.6") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:2.20.6") // 카카오 로그인 API 모듈
    implementation ("com.kakao.sdk:v2-share:2.20.6") // 카카오톡 공유 API 모듈
    implementation ("com.kakao.sdk:v2-talk:2.20.6") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation ("com.kakao.sdk:v2-friend:2.20.6") // 피커 API 모듈
    implementation ("com.kakao.sdk:v2-navi:2.20.6") // 카카오내비 API 모듈
    implementation ("com.kakao.sdk:v2-cert:2.20.6")// 카카오톡 인증 서비스 API 모듈
}