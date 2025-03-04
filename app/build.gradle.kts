plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
//    id ("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin") // Apply Safe Args here
    id("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id("kotlin-parcelize")

}

android {
    namespace = "com.sample.calendar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sample.calendar"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("androidx.preference:preference-ktx:1.2.1")

    // Hilt DI
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03") // Todo:hilt-lifecycle-viewmodel is outdated. Hilt’s lifecycle integration is now part of the core Hilt library, so you can remove the androidx.hilt:hilt-lifecycle-viewmodel dependency entirely.
    implementation ("com.google.dagger:hilt-android:2.49")
    ksp ("com.google.dagger:hilt-compiler:2.48")
//    kapt ("com.google.dagger:hilt-compiler:2.48.1")

    // Coroutine dependencies support
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Navigation components
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.7")

    // Retrofit for API calls
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")

    // Room Database | Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

//    implementation("androidx.room:room-runtime:2.6.1")
//    implementation ("androidx.room:room-ktx:2.6.1")
//    kapt ("androidx.room:room-compiler:2.6.1")


    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")

    implementation ("joda-time:joda-time:2.12.7")

    implementation("com.google.apis:google-api-services-calendar:v3-rev305-1.23.0")
    implementation("com.google.api-client:google-api-client-gson:1.31.5")
    implementation("com.google.http-client:google-http-client-jackson2:1.39.2")
    implementation("com.google.http-client:google-http-client-gson:1.39.2")
    implementation("com.google.api-client:google-api-client-android:1.23.0") {
        exclude(group = "org.apache.httpcomponents")
    }

    implementation ("androidx.activity:activity-ktx:1.10.0")
    implementation ("androidx.fragment:fragment-ktx:1.8.6")

//    implementation("com.kizitonwose.calendar:view:2.0.0")

    implementation ("com.google.android.material:material:1.12.0")

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics:19.4.0")

    implementation("androidx.core:core-splashscreen:1.0.1")
}