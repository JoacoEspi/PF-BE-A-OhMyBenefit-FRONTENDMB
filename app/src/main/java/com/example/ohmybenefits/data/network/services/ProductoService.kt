package com.example.ohmybenefits.data.network.services

import com.example.ohmybenefits.data.model.GeolocationRequest
import com.example.ohmybenefits.data.model.Lugar
import com.example.ohmybenefits.data.model.ProductoDetalleModel
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class ProductoService @Inject constructor(private val service: ProductoApiClient) {

    suspend fun buscarProductoPorId(id: String): ProductoModel{
            return withContext(Dispatchers.IO){
                val response = service.getProductById(id)
                response.body()!!
            }
    }
    suspend fun obtenerDetalleProducto(idProducto: String, idUsuario: String): Response<ProductoDetalleModel> {
        return withContext(Dispatchers.IO) {
            service.detailProduct(idProducto, idUsuario)
        }
    }
    suspend fun obtenerGeolocalizacion(latitud: Double?, longitud: Double?, codigoComercio: Int,direccion: String? ): Response<List<Lugar>>{
        val req = GeolocationRequest(latitud, longitud, codigoComercio, direccion)
        return withContext(Dispatchers.IO){
            service.geolocation(req)
        }
    }
}