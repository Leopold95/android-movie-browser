import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")

    kotlin("plugin.serialization") version "2.2.10"
}

android {
    namespace = "com.leopold95.moviebrowser"
    compileSdk = 36

    buildFeatures{
        buildConfig = true
    }


    defaultConfig {
        applicationId = "com.leopold95.moviebrowser"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keyBearer = "TMDB_API_KEY"
        val keyUrl = "TMB_API_URL"
        val keyImages = "TMB_API_IMAGES"

        val file = if(name == "release") project.rootProject.file("applicaiton.properties")
        else project.rootProject.file("applicaiton.dev.properties")
        val properties = Properties()
        properties.load(file.inputStream())

        buildConfigField("String", keyBearer, properties.getProperty(keyBearer) ?: "")
        buildConfigField("String", keyUrl, properties.getProperty(keyUrl) ?: "")
        buildConfigField("String", keyImages, properties.getProperty(keyImages) ?: "")
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
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //navigation
    implementation(libs.androidx.navigation.compose)

    //DI support with dagger hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.ui)
    ksp(libs.hilt.android.compiler)

    // ViewModel support
    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    ksp(libs.androidx.hilt.compiler)

    //icons
    implementation(libs.androidx.compose.material.icons.extended)

    //gson
    implementation(libs.gson)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)

    //data store prefs
    implementation(libs.androidx.datastore.preferences)

    //async images coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}