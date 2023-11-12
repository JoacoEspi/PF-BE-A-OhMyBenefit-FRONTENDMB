package com.example.ohmybenefits.domain.usecases

import com.example.ohmybenefits.data.repositories.ProductoRepository
import com.example.ohmybenefits.domain.model.Producto
import javax.inject.Inject

class BuscarProductoPorIdUseCase @Inject constructor(
    private val prdoRepository : ProductoRepository
) {
    suspend operator fun invoke(id: String): Producto {
        val producto = prdoRepository.buscarProductoPorId(id)

        return producto
    }

}