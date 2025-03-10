plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.selfphoneonline"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.selfphoneonline"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // RxJava3
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //bradge
    implementation("com.nex3z:notification-badge:1.0.4")

    //even bus
    implementation("org.greenrobot:eventbus:3.2.0")

    //paper
    implementation("io.github.pilgr:paperdb:2.7.1")

    //Gson
    implementation("com.google.code.gson:gson:2.8.9")

    //Lottie
    implementation("com.airbnb.android:lottie:6.6.3")
}