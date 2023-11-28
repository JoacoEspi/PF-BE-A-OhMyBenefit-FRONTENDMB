package com.example.ohmybenefits.core

import android.annotation.SuppressLint
import android.app.Application

class Config : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var preferences: Preferences
        lateinit var baseUrl: String
    }
    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }
}