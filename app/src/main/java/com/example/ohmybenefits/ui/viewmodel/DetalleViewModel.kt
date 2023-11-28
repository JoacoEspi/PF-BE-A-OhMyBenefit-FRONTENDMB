package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.data.model.ProductoDetalleModel
import com.example.ohmybenefits.data.network.services.ProductoService
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val productoService: ProductoService
    ) : ViewModel() {
    private val _detalleProducto = MutableLiveData<ProductoDetalleModel>()
    val detalleProducto: LiveData<ProductoDetalleModel> get() = _detalleProducto

    private val _errorMensaje = MutableLiveData<String>()
    val errorMensaje: LiveData<String> get() = _errorMensaje
    fun cargarDetalleProducto(idProducto: String, idUsuario: String) {
        viewModelScope.launch {
            try {
                val response = productoService.obtenerDetalleProducto(idProducto, idUsuario)
                if (response.isSuccessful) {
                    _detalleProducto.value = response.body()
                } else {
                    _errorMensaje.value = "Error en la solicitud al servidor: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMensaje.value = "Error inesperado: ${e.message}"
            }
        }
    }
    }