package com.example.ohmybenefits.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.core.Preferences
import com.example.ohmybenefits.data.model.ResetContrasenia
import com.example.ohmybenefits.data.model.Seguridad
import com.example.ohmybenefits.data.model.UsuarioApiResponse
import com.example.ohmybenefits.data.model.UsuarioLoginModel
import com.example.ohmybenefits.data.model.UsuarioModel
import com.example.ohmybenefits.data.network.services.UsuarioService
import com.example.ohmybenefits.domain.usecases.RegistrarUsuarioUseCase
import com.example.ohmybenefits.domain.usecases.RestaurarContraseniaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val registrarCase: RegistrarUsuarioUseCase,
    private val usuarioService: UsuarioService,
    private val restaurarCase: RestaurarContraseniaUseCase
) : ViewModel() {

    private val _usuario = MutableLiveData<UsuarioApiResponse>()
    val usuario: LiveData<UsuarioApiResponse> get() = _usuario

    var successMessage: String = " "


    //Atributos  del usuario

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellido = MutableLiveData<String>()
    val apellido: LiveData<String> = _apellido

    private val _telefono = MutableLiveData<String>()
    val telefono: LiveData<String> = _telefono

    private val _mail = MutableLiveData<String>()
    val mail: LiveData<String> get() = _mail

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento: LiveData<String> = _fechaNacimiento

    private val _contrasenia = MutableLiveData<String>()
    val contrasenia: LiveData<String> = _contrasenia

    private val _seguridad = MutableLiveData<Seguridad>()
    val seguridad: LiveData<Seguridad> = _seguridad

    private val _rol = MutableLiveData<String>()
    val rol: LiveData<String> = _rol


    //funciones del usuario

    fun registrarUsuario(
        nombre: String,
        apellido: String,
        telefono: String,
        mail: String,
        fecha: String,
        contrasenia: String,
        preguntaSeg: String,
        respuestaSeg: String
    ) {

        val seguridad = Seguridad(preguntaSeg, respuestaSeg)
        val usuario = UsuarioModel(nombre, apellido, telefono, mail, fecha, contrasenia, seguridad)

        viewModelScope.launch {
            val response = registrarCase(usuario)
            if (response.success) {
                Log.e("Funciona?", response.message!!)
                setNombre(nombre)
                setApellido(apellido)
                setTelefono(telefono)
                setMail(mail)
                setFechaNacimiento(fecha)
                setContrasenia(contrasenia)
                setSeguridad(seguridad)
                setSuccessMsg(response.message)
            } else if (!response.success) {
                throw Exception("Hubo un error: " + response.result)
            }
        }
    }

    fun loginUsuario(email: String, contrasenia: String, context: Context) {
        viewModelScope.launch {
            try {
                 var response = usuarioService.loginUsuario(UsuarioLoginModel(email, contrasenia))
                _mail.value = email
                val prefs = Preferences(context)
                val token = response?.data?.token
                if (token != null) {
                    prefs.guardarToken(token)
                }
                prefs.guardarEmail(email)
                Log.d("Login", response!!.message)
                Log.d("Email:", email)
                setSuccessMsg(response!!.message)
            } catch (e: Exception) {
                throw Exception("Hubo un error: ${e.message}")
            }
        }
    }

    fun resetearContrasenia(
        email: String,
        pregunta: String,
        respuesta: String,
        nuevaContrasenia: String
    ) {
        val seguridad = Seguridad(pregunta, respuesta)
        val nuevosVal = ResetContrasenia(email, nuevaContrasenia, seguridad)

        viewModelScope.launch {
            val response = restaurarCase(nuevosVal)
            if (response.success) {
                setContrasenia(nuevaContrasenia)
                response.message?.let { setSuccessMsg(it) }
            } else {
                throw Exception("Ha ocurrido un error: ${response.result}")
            }
        }
    }

    //Setters
    fun setSuccessMsg(msg: String) {
        this.successMessage = msg
    }

    fun setNombre(nombre: String) {
        _nombre.value = nombre
    }

    fun setApellido(apellido: String) {
        _apellido.value = apellido
    }

    fun setTelefono(telefono: String) {
        _telefono.value = telefono
    }

    fun setMail(mail: String) {
        _mail.value = mail
    }

    fun setFechaNacimiento(fecha: String) {
        _fechaNacimiento.value = fecha
    }

    fun setContrasenia(contrasenia: String) {
        _contrasenia.value = contrasenia
    }

    fun setSeguridad(seguridad: Seguridad) {
        _seguridad.value = seguridad
    }
}