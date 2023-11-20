package com.example.ohmybenefits.data.repositories

import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.services.ProductoService
import com.example.ohmybenefits.domain.model.Producto
import com.example.ohmybenefits.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val remote: ProductoService
) {

    suspend fun buscarProductoPorId(id: String): Producto {
        return withContext(Dispatchers.IO){
            val response: ProductoModel = remote.buscarProductoPorId(id)
            response.toDomain()}
    }
}