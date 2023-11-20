package com.example.ohmybenefits.data.repositories

import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.data.network.interfaces.UsuarioApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
class ComentarioRepository @Inject constructor(private val usuarioApiClient: UsuarioApiClient) {
    suspend fun getComentarios(): Response<List<ComentarioModel>> {
        return withContext(Dispatchers.IO) {
            usuarioApiClient.getComentarios()
        }
    }
}