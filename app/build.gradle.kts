plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.whereyou"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.whereyou"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.android.volley:volley:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.osmdroid:osmdroid-android:6.1.17")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.github.MKergall:osmbonuspack:6.9.0")
    implementation("com.github.parse-community.Parse-SDK-Android:parse:1.26.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.8.1")
    implementation ("com.github.parse-community:ParseLiveQuery-Android:1.2.2")
}