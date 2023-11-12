package com.example.ohmybenefits.data.network.services

import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import com.example.ohmybenefits.domain.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductoService @Inject constructor(private val service: ProductoApiClient) {

    suspend fun buscarProductoPorId(id: String): ProductoModel{
            return withContext(Dispatchers.IO){
                val response = service.getProductById(id)
                response.body()!!
            }
    }
}