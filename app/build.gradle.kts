plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.projeto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projeto"
        minSdk = 24
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation ("com.hbb20:ccp:2.5.2")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.zxing:core:3.4.1") // Adicionando a dependência ZXing
    implementation("com.google.zxing:android-integration:3.3.0")
    implementation("pub.doric.extension:barcodescanner:0.1.16")
    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation ("com.github.santalu:maskara:1.0.0")



}
