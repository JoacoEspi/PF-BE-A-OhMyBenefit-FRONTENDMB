package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName


data class UsuarioModel(
    @SerializedName("_id") val _id: String,
    @SerializedName("mail") val mail: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("fechaNacimiento") val fechaNacimiento: String,
    @SerializedName("contrasenia") val contrasenia: String,
    @SerializedName("presupuesto") val presupuesto: Array<Any?>,
    @SerializedName("seguridad") val seguridad: Any,
    @SerializedName("rol") val rol: String
)
