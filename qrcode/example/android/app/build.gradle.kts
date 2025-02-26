import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.reader(Charsets.UTF_8).use { reader ->
        localProperties.load(reader)
    }
}

val kotlinVersion = "1.9.22"  // Defina a versão do Kotlin

android {
    // Defina o namespace correto
    namespace = "com.example.qrcode"

    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34  // Definição recomendada do targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true  // Habilitando a geração de BuildConfig
    }

    lintOptions {
        disable("InvalidPackage")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}
