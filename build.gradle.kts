// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.21" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false //Might  be 1.8.20 instead of 1.9.0
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
    //id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
    //id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

}