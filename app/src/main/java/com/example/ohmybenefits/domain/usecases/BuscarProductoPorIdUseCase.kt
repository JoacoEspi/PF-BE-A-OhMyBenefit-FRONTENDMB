package com.example.ohmybenefits.domain.usecases

import com.example.ohmybenefits.data.repositories.ProductoRepository
import com.example.ohmybenefits.domain.model.Producto
import javax.inject.Inject

class BuscarProductoPorIdUseCase @Inject constructor(
    private val productoRepository : ProductoRepository
) {
    suspend operator fun invoke(id: String): Producto {
        val producto = productoRepository.buscarProductoPorId(id)

        return producto
    }

}