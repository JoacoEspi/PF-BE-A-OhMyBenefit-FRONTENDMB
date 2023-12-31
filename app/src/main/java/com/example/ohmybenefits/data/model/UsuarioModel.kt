package com.example.ohmybenefits.data.model

data class UsuarioModel(
    val nombre: String?,
    val apellido: String?,
    val telefono: String?,
    val mail: String,
    val fechaNacimiento: String?,
    val contrasenia: String,
    val seguridad: Seguridad?

)

data class Seguridad(
    val pregunta: String,
    val respuesta: String
)


data class ResetContrasenia(
    val mail: String,
    val nuevaContrasenia: String,
    val seguridad: Seguridad
)

data class UsuarioLoginModel(
    val mail: String,
    val contrasenia: String
)


