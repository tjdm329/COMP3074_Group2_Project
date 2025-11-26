plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.comp3074project"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.comp3074project"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    //lifecycle components
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.4")
    //liveData
    implementation("androidx.lifecycle:lifecycle-livedata:2.9.4")
    //lifecycles only (without viewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime:2.9.4")

    //saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.4")

    //annotation processor
    implementation("androidx.lifecycle:lifecycle-common-java8:2.9.4")
}