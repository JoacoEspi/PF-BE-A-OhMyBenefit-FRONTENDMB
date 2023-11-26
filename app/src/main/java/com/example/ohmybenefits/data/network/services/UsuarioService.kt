package com.example.ohmybenefits.data.network.services
import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.model.UsuarioApiResponse
import com.example.ohmybenefits.data.model.UsuarioModel
import com.example.ohmybenefits.data.network.interfaces.UsuarioApiClient
import com.example.ohmybenefits.data.model.ComentarioModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UsuarioService @Inject constructor(
    private val apiClient: UsuarioApiClient
) {

    suspend fun registrarUsuario(usuario: UsuarioModel): UsuarioApiResponse {
        return withContext(Dispatchers.IO){
            val response = apiClient.registro(usuario)
            response.body()!!
        }
    }

    suspend fun loginUsuario(usuario: UsuarioModel): UsuarioApiResponse {
        return withContext(Dispatchers.IO){
            val response = apiClient.login(usuario)
            response.body()!!
        }
    }

    suspend fun restaurarContrasenia(nuevosVal: ResetContrasenia) : UsuarioApiResponse {
        return withContext(Dispatchers.IO){
            val response = apiClient.restablecerContrasenia(nuevosVal)
            response.body()!!
        }
    }
    
    suspend fun postComment(comment: ComentarioModel): Response<ComentarioModel> {
        return withContext(Dispatchers.IO){
            apiClient.postComment(comment)
        }
    }
}