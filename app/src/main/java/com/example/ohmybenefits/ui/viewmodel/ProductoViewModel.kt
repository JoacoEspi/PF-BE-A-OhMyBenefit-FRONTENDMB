package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.domain.model.Producto
import com.example.ohmybenefits.domain.usecases.BuscarProductoPorIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val buscarProductoPorId : BuscarProductoPorIdUseCase
) : ViewModel() {

    private val _producto = MutableLiveData<Producto>()
    val producto: LiveData<Producto> = _producto

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}
