package com.example.ohmybenefits.ui

import android.app.Application
import com.example.ohmybenefits.R
import com.example.ohmybenefits.core.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OhMyBenefitsApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Config.apiKey = resources.getString(R.string.api_key)
        Config.baseUrl = resources.getString(R.string.base_url)
    }
}