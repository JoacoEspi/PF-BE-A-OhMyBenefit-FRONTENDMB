package com.example.ohmybenefits

import android.app.Application
import com.example.ohmybenefits.core.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OhMyBenefitsApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Config.baseUrl = resources.getString(R.string.base_url)
    }
}