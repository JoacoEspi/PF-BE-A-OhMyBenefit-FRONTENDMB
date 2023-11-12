package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName

data class ProductoModel(
    @SerializedName("_id") val _id: String,
    @SerializedName("codigo") val codigo: Number,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("precio") val precio: String,
    @SerializedName("categorias") val categorias: ArrayList<String>,
    @SerializedName("imagenUrl") val imagenUrl: String,
    @SerializedName("idComercio") val idComercio: String
)
