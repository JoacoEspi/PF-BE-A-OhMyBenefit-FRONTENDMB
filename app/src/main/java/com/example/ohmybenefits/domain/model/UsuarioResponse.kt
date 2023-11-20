package com.example.ohmybenefits.domain.model

import com.example.ohmybenefits.data.model.Data
import com.example.ohmybenefits.data.model.UsuarioApiResponse

data class UsuarioResponse(
    val data: Data?,
    val success: Boolean,
    val message: String
)


 fun UsuarioApiResponse.toDomain() = UsuarioResponse(data, success, message)
