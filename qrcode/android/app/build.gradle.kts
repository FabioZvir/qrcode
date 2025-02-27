plugins {
    id("com.android.library") 
    id("org.jetbrains.kotlin.android") 
}

repositories {
    google() // Repositório do Google para plugins Android
    mavenCentral() // Repositório Maven Central
    gradlePluginPortal() // Repositório do Gradle Plugin Portal
}

android {
    namespace = "com.example.qrcode"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    buildFeatures {
        buildConfig = true
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("com.google.zxing:core:3.5.1") // ZXing para leitura de QR Code
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") // Biblioteca para QR Code
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
