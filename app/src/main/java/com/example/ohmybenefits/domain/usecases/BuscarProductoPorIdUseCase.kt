package com.example.ohmybenefits.domain.usecases

import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.repositories.ProductoRepository
import javax.inject.Inject

class BuscarProductoPorIdUseCase @Inject constructor(
    private val productoRepository : ProductoRepository
) {
    suspend operator fun invoke(id: String): ProductoModel {
        val producto = productoRepository.buscarProductoPorId(id)

        return producto
    }

}