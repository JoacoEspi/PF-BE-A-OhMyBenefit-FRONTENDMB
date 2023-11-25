package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.data.network.services.UsuarioService
import com.example.ohmybenefits.data.repositories.ComentarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ComentarioViewModel @Inject constructor(
    private val comentarioRepository: ComentarioRepository,
    private val service: UsuarioService
) : ViewModel() {

    private val _errorMensaje = MutableLiveData<String?>()
    val errorMensaje: LiveData<String?> = _errorMensaje

    private val _comentarioExitoso = MutableLiveData<Boolean>()
    val comentarioExitoso: LiveData<Boolean> = _comentarioExitoso

    private val _mensajeExito = MutableLiveData<String?>()
    val mensajeExito: LiveData<String?> = _mensajeExito

    private val _comentarioResponse = MutableLiveData<ComentarioModel?>()
    val comentarioResponse: LiveData<ComentarioModel?> = _comentarioResponse

    private val _comentarioLiveData = MutableLiveData<Response<List<ComentarioModel>>>()
    val comentarioLiveData: LiveData<Response<List<ComentarioModel>>> = _comentarioLiveData

    fun postComment(comment: ComentarioModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ComentarioModel> = service.postComment(comment)
                if (response.isSuccessful) {
                    val comentarioExitoso = response.body()
                    Log.d("ComentarioViewModel", "Comentario exitoso: $comentarioExitoso")

                    _comentarioResponse.postValue(comentarioExitoso)
                    _mensajeExito.postValue("Comentario enviado con éxito")
                    _comentarioExitoso.postValue(comentarioExitoso != null)
                } else {
                    _errorMensaje.postValue("Error en la solicitud al servidor: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ComentarioViewModel", "Error inesperado: ${e.message}")
                _errorMensaje.postValue("Error inesperado: ${e.message}")
            }
        }
    }

    fun getComentarios() {
        viewModelScope.launch {
            try {
                val response: Response<List<ComentarioModel>> = comentarioRepository.getComentarios()
                if (response.isSuccessful) {
                    val comentarios = response
                    _comentarioLiveData.postValue(comentarios)
                } else {
                    _errorMensaje.postValue("Error en la solicitud al servidor: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMensaje.postValue("Error inesperado: ${e.message}")
            }
        }
    }
}

