package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName

class ProductoDetalleModel(
        val producto: Producto,
        val recomendaciones: Recomendaciones
    )
    data class Producto(
        val _id: String,
        val codigo: Long,
        val nombre: String,
        val precio: String,
        val categorias: List<String>,
        val imageUrl: String,
        val idComercio: String,
        val __v: Int
    )
    data class Recomendaciones(
        val recommId: String,
        val recomms: List<Recomendacion>,
        val numberNextRecommsCalls: Int
    )
    data class Recomendacion(
        val id: String,
        val values: RecomendacionValues
    )
data class RecomendacionValues(
    val categorias: String,
    val precio: String,
    val nombre: String,
    @SerializedName("imageUrl")
    val imageUrl: List<String>,
    @SerializedName("idComercio")
    val idComercio: Int,
    @SerializedName("codigo")
    val codigo: Long
)
