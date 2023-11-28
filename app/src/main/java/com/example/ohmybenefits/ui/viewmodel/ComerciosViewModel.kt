package com.example.ohmybenefits.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.data.model.GeolocationModel
import com.example.ohmybenefits.data.model.Lugar
import com.example.ohmybenefits.data.network.services.ProductoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ComerciosViewModel @Inject constructor(private val productoService: ProductoService) : ViewModel() {

    private val _comerciosResponse = MutableLiveData<Response<List<Lugar>>>()
    val comerciosResponse: LiveData<Response<List<Lugar>>> = _comerciosResponse

    fun obtenerComercios(latitud: Double?, longitud: Double?, codigoComercio: Int, direccion: String?) {
        viewModelScope.launch {
            val response = productoService.obtenerGeolocalizacion(latitud, longitud, codigoComercio, direccion)
            _comerciosResponse.postValue(response)
        }
    }
}

