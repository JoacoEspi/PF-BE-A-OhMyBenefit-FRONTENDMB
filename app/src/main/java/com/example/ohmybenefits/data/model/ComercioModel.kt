package com.example.ohmybenefits.data.model

import com.example.ohmybenefits.data.enums.Categorias
import com.google.gson.annotations.SerializedName

data class ComercioModel(
    @SerializedName("_id") val id: String,
    @SerializedName("codComercio") val codComercio: String,
    @SerializedName("razonSocial") val razonSocial: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("direccion") val direccion: String, //hay que ver como lo devuelve el json
    @SerializedName("productos") val productos: List<String>,
    @SerializedName("categorias") val categorias: Categorias //o seria List<String>
)
