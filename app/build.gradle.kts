plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.kuzmin.weatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kuzmin.weatherforecast"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    // optional - ReactiveStreams support for LiveData
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")

    implementation("androidx.cardview:cardview:1.0.0")

    val hiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    implementation ("androidx.hilt:hilt-work:1.2.0")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")

    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation ("com.squareup.retrofit2:converter-gson:${retrofitVersion}")

    implementation("com.google.gms.google-services:com.google.gms.google-services.gradle.plugin:4.4.2")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation("androidx.work:work-runtime-ktx:2.8.0")

    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:${roomVersion}")
    implementation("androidx.room:room-common:${roomVersion}")
    kapt("androidx.room:room-compiler:${roomVersion}")
    implementation("androidx.room:room-paging:${roomVersion}")
    implementation("androidx.room:room-ktx:${roomVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment:${navVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${navVersion}")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:${navVersion}")

    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.annotation:annotation:1.9.1")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // https://mavenlibs.com/maven/dependency/com.diamondedge/charts-android
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}