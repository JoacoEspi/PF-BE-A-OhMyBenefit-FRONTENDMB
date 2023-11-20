package com.example.ohmybenefits.domain.model

import com.example.ohmybenefits.data.model.ProductoModel

data class Producto(
    val _id: String,
    val codigo: Number,
    val nombre: String,
    val precio: String,
    val categorias: ArrayList<String>,
    val imageUrl: String,
    val idComercio: String
)


fun ProductoModel.toDomain() = Producto(
    _id,
    codigo,
    nombre,
    precio,
    categorias,
    imageUrl,
    idComercio
)