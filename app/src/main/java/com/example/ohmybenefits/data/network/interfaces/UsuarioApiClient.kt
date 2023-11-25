package com.example.ohmybenefits.data.network.interfaces

import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.data.model.PresupuestoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioApiClient {
    @POST("/user/analyze-sentiment")
    suspend fun postComment(@Body comment: ComentarioModel): Response<ComentarioModel>

    @GET("/user/analyze-sentiment/commentslistAll")
    suspend fun getComentarios(): Response<List<ComentarioModel>>

    @POST("/user/estimate-cart-price")
    suspend fun guardarPresupuesto(@Body presupuesto: PresupuestoModel): Response<Unit>
}