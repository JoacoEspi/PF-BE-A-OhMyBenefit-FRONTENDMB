package com.example.ohmybenefits.di

import com.example.ohmybenefits.core.Config
import com.example.ohmybenefits.core.InterceptorCustom
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private var client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(InterceptorCustom)
    }.build()
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideProductoApiClient(retrofit: Retrofit): ProductoApiClient{
        return retrofit.create(ProductoApiClient::class.java)
    }

}