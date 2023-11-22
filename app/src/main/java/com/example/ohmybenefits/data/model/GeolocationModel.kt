package com.example.ohmybenefits.data.model
data class GeolocationModel(
    val lugares: List<Lugar>
)

data class Lugar(
    val nombre: String,
    val direccion: String,
    val coordenadas: Coordenadas,
    val distancia: Double
)

data class Coordenadas(
    val lat: Double,
    val lng: Double
)