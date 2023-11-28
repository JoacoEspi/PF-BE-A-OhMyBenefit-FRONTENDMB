package com.example.ohmybenefits.data.repositories

import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.model.UsuarioApiResponse
import com.example.ohmybenefits.data.model.UsuarioModel
import com.example.ohmybenefits.data.network.services.UsuarioService
import com.example.ohmybenefits.domain.model.UsuarioResponse
import com.example.ohmybenefits.domain.model.toDomain
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val service: UsuarioService
){

    suspend fun registrarUsuario(usuario: UsuarioModel): UsuarioResponse {
        val response: UsuarioApiResponse = service.registrarUsuario(usuario)
        return response.toDomain()
    }

    suspend fun restaurarContrasenia(nuevosVal: ResetContrasenia) : UsuarioResponse {
        val response: UsuarioApiResponse = service.restaurarContrasenia(nuevosVal)
        return response.toDomain()
    }
}