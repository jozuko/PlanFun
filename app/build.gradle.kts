import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jozu.compose.planfun"
    compileSdk = 33

    val localProperties = gradleLocalProperties(rootDir)

    defaultConfig {
        applicationId = "com.jozu.compose.planfun"
        minSdk = 26
        // AGP roadmap https://developer.android.com/studio/releases/gradle-plugin-roadmap?hl=ja
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file(project.rootProject.file(localProperties.getProperty("keystore.file")))
            storePassword = localProperties.getProperty("keystore.storePassword")
            keyAlias = localProperties.getProperty("keystore.alias")
            keyPassword = localProperties.getProperty("keystore.keyPassword")
        }
        create("release") {
            storeFile = file(project.rootProject.file(localProperties.getProperty("keystore.file")))
            storePassword = localProperties.getProperty("keystore.storePassword")
            keyAlias = localProperties.getProperty("keystore.alias")
            keyPassword = localProperties.getProperty("keystore.keyPassword")
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    // compose
    val composeVersion = "1.4.3"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.1")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    runtimeOnly("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // hilt
    val hiltVersion = "2.47"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")

    // for GoogleSignIn
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}