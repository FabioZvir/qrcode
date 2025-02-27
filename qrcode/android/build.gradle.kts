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

    namespace = "com.seu.pacote.msk"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
        }
    }
}

dependencies {
    implementation 'com.android.tools:desugar_jdk_libs:1.1.5' // Necessário para o desugaring
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20")
}
