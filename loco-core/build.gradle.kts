import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("jacoco")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
    lintOptions {
        isAbortOnError = true
    }
    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")
    sourceSets["androidTest"].java.srcDir("src/androidTest/kotlin")
    buildFeatures.buildConfig = false
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(Libs.appcompat)
    implementation(Libs.lifecycle_runtime_ktx)

    debugImplementation(Libs.fragment_testing) {
        exclude("androidx.test", "monitor")
    }

    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_ktx)
    androidTestImplementation(Libs.coroutines_test_extensions)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.junit_ktx)
    androidTestImplementation(Libs.kotlinx_coroutines_debug)
    androidTestImplementation(Libs.lifecycle_runtime_testing)
}

apply(from = "$rootDir/gradle/deploy.gradle")