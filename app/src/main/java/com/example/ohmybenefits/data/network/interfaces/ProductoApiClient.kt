package com.example.ohmybenefits.data.network.interfaces

import com.example.ohmybenefits.data.model.GeolocationRequest
import com.example.ohmybenefits.data.model.Lugar
import com.example.ohmybenefits.data.model.ProductoDetalleModel
import com.example.ohmybenefits.data.model.ProductoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductoApiClient {

    @GET("/product/list-all")
    suspend fun listAllProducts(): Response<List<ProductoModel>>

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<ProductoModel>

    @GET("/product/list-all/{category}")
    suspend fun listByCategory(@Path("category") category: String): Response<List<ProductoModel>>

    @GET("/product/search-name/{name}")
    suspend fun searchByName(@Path("name") name: String): Response<List<ProductoModel>>

    @GET("/product/search/{word}")
    suspend fun searchByWord(@Path("word") word: String): Response<List<ProductoModel>>

    @DELETE("/product/{id}")
    suspend fun deleteProductById(@Path("id") id: String): Response<ProductoModel>

    @GET("/product/{idProducto}/{idUsuario}")
    suspend fun detailProduct(
        @Path("idProducto") idProducto: String,
        @Path("idUsuario") idUsuario: String
    ): Response<ProductoDetalleModel>

    @POST("/product/geolocation")
    suspend fun geolocation(
        @Body request: GeolocationRequest
        ): Response<List<Lugar>>
}