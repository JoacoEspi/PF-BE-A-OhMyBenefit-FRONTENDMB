package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.services.ProductoService
import com.example.ohmybenefits.domain.model.Producto
import com.example.ohmybenefits.domain.usecases.BuscarProductoPorIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoService: ProductoService
) : ViewModel() {

    private val _listaProducto = MutableLiveData<List<ProductoModel>>()
    val listaProducto: LiveData<List<ProductoModel>> = _listaProducto

    fun setListaProducto(listaProducto: List<ProductoModel>) {
        _listaProducto.value = listaProducto
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate() {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val response = productoService.listarProductos(5,5)
                Log.d("ProductoViewModel", "Lista de productos: $response")
                if (response != null) {
                    setListaProducto(response)
                }
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }

    fun clear() {
        _listaProducto.value = emptyList()
    }
}
