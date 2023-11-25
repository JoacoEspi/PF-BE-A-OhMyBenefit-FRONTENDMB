package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.data.network.services.ProductoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(private val productService: ProductoService):
    ViewModel() {

    private val _categorySelected = MutableLiveData<String>()
    private val _productList = MutableLiveData<List<ProductoModel>>()
    val productList: LiveData<List<ProductoModel>> get() = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setListaProducto(listaProducto: List<ProductoModel>) {
        _productList.value = listaProducto
    }

    private var currentPage = 1

    fun listarProductos() {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val productList = productService.listarProductos(currentPage, PER_PAGE)
                Log.d("ProductoViewModel", "Product list: $productList")
                setListaProducto((_productList.value ?: emptyList()) + productList.docs)
                currentPage++
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error: ${e.message}")
            } finally {
                setIsLoading(false)
            }
        }
    }

    fun listarProductosPorCategoria() {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val productList = productService.listarProductosPorCategoria(
                    _categorySelected.value.toString(),
                    currentPage,
                    PER_PAGE
                )
                Log.d("ProductoViewModel", "Product list by category: $productList")
                setListaProducto((_productList.value ?: emptyList()) + productList)
                currentPage++
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error: ${e.message}")
            } finally {
                setIsLoading(false)
            }
        }
    }

    fun buscarPalabra(palabra: String) {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val productList = productService.buscarPalabra(palabra)
                Log.d("ProductoViewModel", "Search products: $productList")
                setListaProducto((_productList.value ?: emptyList()) + productList)
                blockScrollLoad()
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error: ${e.message}")
            } finally {
                setIsLoading(false)
            }
        }
    }

    companion object {
        const val PER_PAGE = 5
    }

    fun clear() {
        _productList.value = emptyList()
        currentPage = 1
    }

    fun blockScrollLoad() {
        currentPage = -1
    }

    fun isScrollLoadingDisabled(): Boolean {
        return currentPage == -1
    }

    fun filtrarProductos(isScrollFilter: Boolean) {
        if (isScrollFilter && isScrollLoadingDisabled()) {
            return
        }
        if (_categorySelected.value.isNullOrBlank()) {
            listarProductos()
        } else {
            listarProductosPorCategoria()
        }
    }

    fun setCategory(categoria: String) {
        if (_categorySelected.value.toString().equals(categoria)) {
            _categorySelected.value = ""
        } else {
            _categorySelected.value = categoria
        }
    }
}
