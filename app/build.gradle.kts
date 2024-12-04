plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.mobile.wishtrack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobile.wishtrack"
        minSdk = 21
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

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.mpandroidchart);

    implementation(libs.glide);
    annotationProcessor(libs.glide);

    implementation(libs.lombok);
    annotationProcessor(libs.lombok);

    implementation(libs.gson);

    implementation(libs.retrofit);
    implementation(libs.retrofit.converter.gson);
    implementation(libs.okhttp);
}