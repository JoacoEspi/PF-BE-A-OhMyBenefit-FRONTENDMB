package com.example.ohmybenefits.core

import okhttp3.Interceptor
import okhttp3.Response
object InterceptorCustom : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = Config.apiKey

        var request = chain.request()
        request = request.newBuilder()
            .header("X-Api_Key", apiKey ?: "")
            .header("Accept","application/json")
            .header("Content-Type", "applictaion/json")
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }


}