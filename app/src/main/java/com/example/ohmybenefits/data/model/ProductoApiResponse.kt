package com.example.ohmybenefits.data.model

data class ProductoApiResponse(
    val docs: List<ProductoModel>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int
)
