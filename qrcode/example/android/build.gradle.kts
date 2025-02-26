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
    // Defina o namespace correto para a sua biblioteca
    namespace = "com.example.qrcode"

    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34  // Definição recomendada do targetSdk
        // A biblioteca não precisa de versionCode e versionName, mas você pode definir se necessário
        // versionCode = 1
        // versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true  // Habilitando a geração de BuildConfig
    }

    lintOptions {
        disable("InvalidPackage")
    }

    // Configurações de compatibilidade com Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Garantir compatibilidade com Java 1.8
        targetCompatibility = JavaVersion.VERSION_1_8 // Garantir compatibilidade com Java 1.8
    }

    // Definir a versão do Kotlin JVM
    kotlinOptions {
        jvmTarget = "1.8" // Alinhar o Kotlin com o Java 1.8
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}
