plugins {
    id("com.google.gms.google-services")
    id("com.android.application")
    kotlin("android")
    apply{"com.android.application"}
    apply{"com.google.gms.google-services"}
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.deny.ibitur.android"
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
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation("com.google.android.gms:play-services-auth:20.1.0")

    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")


    // Required only if Facebook login support is required
    // Find the latest Facebook SDK releases here: https://goo.gl/Ce5L94
    //implementation("com.facebook.android:facebook-android-sdk:8")

}