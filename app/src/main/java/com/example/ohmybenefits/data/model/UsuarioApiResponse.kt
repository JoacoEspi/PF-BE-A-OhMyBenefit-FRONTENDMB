package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName

data class UsuarioApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("result") val result: String?,
    @SerializedName("data") val data: Data?
)

data class Data(
    val token: String
)
