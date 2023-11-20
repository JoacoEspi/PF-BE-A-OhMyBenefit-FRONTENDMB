package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName

data class UsuarioApiResponse(
    @SerializedName("data") val data: Data?,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class Data(
    val token: String
)
