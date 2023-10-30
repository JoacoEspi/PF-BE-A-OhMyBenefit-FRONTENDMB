package com.example.ohmybenefits.data.network

import com.example.ohmybenefits.data.model.ProductoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoApiClient {

@GET("/product/list-all")
suspend fun listAllProducts(): Call<List<ProductoModel>>

@GET("/product/{id}")
suspend fun getProductById(@Path("id") id: String) : Call<ProductoModel>

@GET("/product/list-all/{category}")
suspend fun listByCategory(@Path("category") category: String): Call<List<ProductoModel>>

@GET("/product/search-name/{name}")
suspend fun searchByName(@Path("name") name: String): Call<List<ProductoModel>>

@GET("/product/search/{word}")
suspend fun searchByWord(@Path("word") word: String): Call<List<ProductoModel>>

@DELETE("/product/{id}")
suspend fun deleteProductById(@Path("id") id: String): Call<ProductoModel>



}