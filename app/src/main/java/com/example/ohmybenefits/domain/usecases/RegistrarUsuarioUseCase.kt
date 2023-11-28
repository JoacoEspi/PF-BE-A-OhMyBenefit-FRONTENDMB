package com.example.ohmybenefits.domain.usecases

import com.example.ohmybenefits.data.model.UsuarioModel
import com.example.ohmybenefits.data.repositories.UsuarioRepository
import com.example.ohmybenefits.domain.model.UsuarioResponse
import javax.inject.Inject

class RegistrarUsuarioUseCase  @Inject constructor(
    private val repositorio : UsuarioRepository
){
    suspend operator fun invoke(usuario: UsuarioModel): UsuarioResponse {
        return repositorio.registrarUsuario(usuario)

    }
}