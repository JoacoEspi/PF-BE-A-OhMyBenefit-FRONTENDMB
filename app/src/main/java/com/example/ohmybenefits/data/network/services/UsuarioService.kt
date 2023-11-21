package com.example.ohmybenefits.data.network.services

import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.data.network.interfaces.UsuarioApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UsuarioService @Inject constructor(private val service: UsuarioApiClient) {
    suspend fun postComment(comment: ComentarioModel): Response<ComentarioModel> {
        return withContext(Dispatchers.IO){
            service.postComment(comment)
        }
    }
}