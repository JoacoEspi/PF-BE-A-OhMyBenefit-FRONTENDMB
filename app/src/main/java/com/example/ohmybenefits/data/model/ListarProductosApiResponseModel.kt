package com.example.ohmybenefits.data.model

data class ListarProductosApiResponseModel(
    val docs: ArrayList<ProductoModel>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int
)
