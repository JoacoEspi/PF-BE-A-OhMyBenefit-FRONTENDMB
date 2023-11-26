package com.example.ohmybenefits.data.network.services
import com.example.ohmybenefits.data.model.ProductoApiResponse
import com.example.ohmybenefits.data.model.GeolocationRequest
import com.example.ohmybenefits.data.model.Lugar
import com.example.ohmybenefits.data.model.ProductoDetalleModel
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.interfaces.ProductoApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
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

    suspend fun buscarPalabra(palabra: String): List<ProductoModel> {
        return withContext(Dispatchers.IO) {
            val response = api.buscarPalabra(palabra)
            response.body()!!
        }
    }

    suspend fun listarProductos(page: Int, perPage: Int): ProductoApiResponse {
        return api.listarProductos(page, perPage)
    }

    suspend fun listarProductosPorCategoria(categoria: String, page: Int, perPage: Int): List<ProductoModel> {
        return withContext(Dispatchers.IO) {
            val response = api.listarProductosPorCategoria(categoria, page, perPage)
            response.docs ?: emptyList()
        }
    }
    suspend fun obtenerDetalleProducto(idProducto: String, idUsuario: String): Response<ProductoDetalleModel> {
        return withContext(Dispatchers.IO) {
            api.detailProduct(idProducto, idUsuario)
        }
    }
    suspend fun obtenerGeolocalizacion(latitud: Double?, longitud: Double?, codigoComercio: Int,direccion: String? ): Response<List<Lugar>>{
        val req = GeolocationRequest(latitud, longitud, codigoComercio, direccion)
        return withContext(Dispatchers.IO){
            api.geolocation(req)
        }
    }
}