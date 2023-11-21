package com.example.ohmybenefits.data.network.services

import com.example.ohmybenefits.data.model.ProductoApiResponse
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductoService @Inject constructor(
    private val api: ProductoApiClient
) {

    suspend fun buscarProductoPorId(id: String): ProductoModel {
        return withContext(Dispatchers.IO) {
            val response = api.buscarProductoPorId(id)
            response.body()!!
        }
    }

    suspend fun buscarProductoPorNombre(nombre: String): ProductoModel {
        return withContext(Dispatchers.IO) {
            val response = api.buscarProductoPorNombre(nombre)
            response.body()!!
        }
    }

    suspend fun buscarPalabra(palabra: String): ProductoModel {
        return withContext(Dispatchers.IO) {
            val response = api.buscarProductoPorNombre(palabra)
            response.body()!!
        }
    }

    suspend fun listarProductos(page: Int, perPage: Int): ProductoApiResponse {
        return api.listarProductos(page, perPage)
    }

    suspend fun listarProductosPorCategoria(categoria: String): List<ProductoModel> {
        return withContext(Dispatchers.IO) {
            val response = api.listarProductosPorCategoria(categoria)
            response.body() ?: emptyList()
        }
    }
}