plugins {
    id("com.google.gms.google-services")
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.deny.ibitur.app"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
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
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.firebase:firebase-bom:29.2.1")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    implementation("com.google.android.gms:play-services-auth:20.1.0")
    implementation("com.google.android.gms:play-services-maps:18.0.1")

    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")

    // Koin main features for Android
    implementation("io.insert-koin:koin-android:3.2.0-beta-1")
    // No more koin-android-viewmodel, koin-android-scope, koin-android-fragment

    // Java Compatibility
    implementation("io.insert-koin:koin-android-compat:3.2.0-beta-1")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:3.2.0-beta-1")
    // Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0-beta-1")
    // Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.2.0-beta-1")
    // Koin Core features
    implementation("io.insert-koin:koin-core:3.2.0-beta-1")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:3.2.0-beta-1")

    //AboutPage
    implementation("io.github.medyo:android-about-page:2.0.0")

    //LOTTIE
    implementation("com.airbnb.android:lottie:5.0.1")
    // Required only if Facebook login support is required
    // Find the latest Facebook SDK releases here: https://goo.gl/Ce5L94
    //implementation("com.facebook.android:facebook-android-sdk:8")

    //float action menu
    implementation("com.github.clans:fab:1.6.4")


}