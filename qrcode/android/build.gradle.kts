plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    namespace = "com.example.qrcode"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.google.zxing:core:3.5.1") // Biblioteca ZXing para leitura de QR Code
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") // Biblioteca para integrar ZXing com Android
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20") // Kotlin Standard Library
}