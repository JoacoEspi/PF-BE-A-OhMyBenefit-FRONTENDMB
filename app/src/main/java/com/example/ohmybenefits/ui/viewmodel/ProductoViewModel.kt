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
class ProductoViewModel @Inject constructor(private val productService: ProductoService) :
    ViewModel() {

    private val _productList = MutableLiveData<List<ProductoModel>>()
    private val _productSearchList = MutableLiveData<List<ProductoModel>>()
    private val _productByCategoryList = MutableLiveData<List<ProductoModel>>()

    val productList: LiveData<List<ProductoModel>> get() = _productList
    val productSearchList: LiveData<List<ProductoModel>> get() = _productSearchList
    val productByCategoryList: LiveData<List<ProductoModel>> get() = _productByCategoryList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setListaProducto(listaProducto: List<ProductoModel>) {
        _productList.value = listaProducto
    }

    fun setListaProductoBuscado(lista: List<ProductoModel>) {
        _productSearchList.value = lista
    }

    fun setListaProductoBuscadoPorCategoria(lista: List<ProductoModel>) {
        _productByCategoryList.value = lista
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

    fun listarProductosPorCategoria(categoria: String) {
        clearCategoryProducts()
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val productList = productService.listarProductosPorCategoria(categoria, currentPage, PER_PAGE)
                Log.d("ProductoViewModel", "Product list by category: $productList")
                setListaProductoBuscadoPorCategoria((_productByCategoryList.value ?: emptyList()) + productList)
                currentPage++
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error: ${e.message}")
            } finally {
                setIsLoading(false)
            }
        }
    }

    fun buscarPalabra(palabra: String) {
        clearSearchProducts()
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val productList = productService.buscarPalabra(palabra)
                Log.d("ProductoViewModel", "Search products: $productList")
                setListaProductoBuscado((_productSearchList.value ?: emptyList()) + productList)
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
        _productSearchList.value = emptyList()
        _productByCategoryList.value = emptyList()
        currentPage = 1
    }

    fun clearSearchProducts() {
        _productSearchList.value = emptyList()
    }

    fun clearCategoryProducts() {
        _productByCategoryList.value = emptyList()
    }
}
