plugins {
  id("com.android.application")
  kotlin("android")
  id("com.google.devtools.ksp")
}

android {
  namespace = "com.example.mindtrack"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.mindtrack"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables { useSupportLibrary = true }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
  buildFeatures { viewBinding = true }

  packaging {
    resources { excludes += listOf("META-INF/AL2.0", "META-INF/LGPL2.1") }
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.15.0")
  implementation("androidx.appcompat:appcompat:1.7.1")
  implementation("com.google.android.material:material:1.13.0")
  implementation("androidx.activity:activity-ktx:1.10.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
  implementation("androidx.recyclerview:recyclerview:1.4.0")

  // Room + KSP
  implementation("androidx.room:room-runtime:2.8.0")
  implementation("androidx.room:room-ktx:2.8.0")
  ksp("androidx.room:room-compiler:2.8.0")

  // Unit test (JVM)
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.robolectric:robolectric:4.14.1")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

  // Android test (instrumented)
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}