package com.example.ohmybenefits.data.network.interfaces

import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.model.UsuarioApiResponse
import com.example.ohmybenefits.data.model.UsuarioModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiClient {



    //Registrar
    @POST("/user/registry")
    suspend fun registro(@Body usuario: UsuarioModel) : Response<UsuarioApiResponse>

    //Login
    @POST("/user/login")
    suspend fun login(@Body usuario: UsuarioModel) : Response<UsuarioApiResponse>

    //Restablecer contrasenia

    @POST("/user/forget-password")
    suspend fun restablecerContrasenia(@Body nuevosVal: ResetContrasenia) : Response<UsuarioApiResponse>


}