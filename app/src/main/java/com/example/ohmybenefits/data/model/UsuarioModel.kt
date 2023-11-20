package com.example.ohmybenefits.data.model

data class UsuarioModel(
    val nombre: String,
   val apellido: String,
    val telefono: String,
    val mail: String,
    val fechaNacimiento: String,
    val contrasenia: String,
    val seguridad: Seguridad

)

data class Seguridad(
    val pregunta: String,
    val respuesta: String
)

