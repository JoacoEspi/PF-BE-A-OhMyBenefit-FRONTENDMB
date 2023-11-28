package com.example.ohmybenefits.data.network.services

import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.data.model.LoginApiResponse
import com.example.ohmybenefits.data.model.PresupuestoModel
import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.model.UsuarioApiResponse
import com.example.ohmybenefits.data.model.UsuarioLoginModel
import com.example.ohmybenefits.data.model.UsuarioModel
import com.example.ohmybenefits.data.network.interfaces.UsuarioApiClient
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

     suspend fun loginUsuario(usuario: UsuarioLoginModel): LoginApiResponse {
        return apiClient.login(usuario)
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
    suspend fun guardarPresupuesto(presupuesto: PresupuestoModel): Response<Unit> {
        return withContext(Dispatchers.IO) {
            apiClient.guardarPresupuesto(presupuesto)
        }
    }
}