package com.example.ohmybenefits.core

import com.example.ohmybenefits.core.Config.Companion.preferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("auth-token", preferences.obtenerToken())
            .build()
        return chain.proceed(request)
    }
}