package com.example.ohmybenefits.domain.model

import com.example.ohmybenefits.data.model.Data
import com.example.ohmybenefits.data.model.UsuarioApiResponse

data class UsuarioResponse(
    val success: Boolean,
    val message: String?,
    val result: String?,
    val data: Data?,
)


 fun UsuarioApiResponse.toDomain() = UsuarioResponse(success, message, result, data)
