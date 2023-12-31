package com.example.ohmybenefits.di

import com.example.ohmybenefits.core.Config
import com.example.ohmybenefits.core.HeaderInterceptor
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import com.example.ohmybenefits.data.network.interfaces.UsuarioApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private var client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HeaderInterceptor())
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
    fun provideProductoApiClient(retrofit: Retrofit): ProductoApiClient {
        return retrofit.create(ProductoApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideUsuarioApiClient(retrofit: Retrofit): UsuarioApiClient{
        return retrofit.create(UsuarioApiClient::class.java)
    }
}