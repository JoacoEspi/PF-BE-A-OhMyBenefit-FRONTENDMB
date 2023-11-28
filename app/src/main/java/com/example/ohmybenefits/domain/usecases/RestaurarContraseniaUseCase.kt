package com.example.ohmybenefits.domain.usecases

import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.repositories.UsuarioRepository
import com.example.ohmybenefits.domain.model.UsuarioResponse
import javax.inject.Inject

class RestaurarContraseniaUseCase @Inject constructor(
    private val repositorio: UsuarioRepository
) {
    suspend operator fun invoke(nuevosVal : ResetContrasenia) : UsuarioResponse {
        return repositorio.restaurarContrasenia(nuevosVal)
    }

}