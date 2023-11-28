package com.example.ohmybenefits.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ohmybenefits.adapters.PresupuestoAdapter
import com.example.ohmybenefits.data.model.PresupuestoModel
import com.example.ohmybenefits.data.model.Producto
import com.example.ohmybenefits.data.network.services.UsuarioService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PresupuestoViewModel @Inject constructor(
    private val service: UsuarioService
): ViewModel() {
    private val _presupuesto = MutableLiveData<Double>()
    private val adapter = PresupuestoAdapter()
    val presupuesto: LiveData<Double> get() = _presupuesto
    private val _productosSeleccionados = MutableLiveData<List<ProductoParaPresupuesto>>()
    val productosSeleccionados: LiveData<List<ProductoParaPresupuesto>> get() = _productosSeleccionados
    fun agregarProducto(producto: Producto, cantidad: Int) {
        val valorPresupuesto: Double = presupuesto.value ?: 0.0
        val productoParaPresupuesto = ProductoParaPresupuesto(producto, cantidad, producto.precio, valorPresupuesto)
        val listaActual = _productosSeleccionados.value.orEmpty().toMutableList()
        listaActual.add(productoParaPresupuesto)
        _productosSeleccionados.value = listaActual
        calcularTotal()
    }
    fun calcularTotal() {
        var total = 0.0
        productosSeleccionados.value?.forEach { productoConCantidad ->
            val precioString = productoConCantidad.producto.precio
            val tieneComa = "," in precioString
            val precioLimpio = precioString.replace("[^\\d.]".toRegex(), "")
            var precio = precioLimpio.toDouble()
            if (tieneComa) {
                precio /= 100
            }
            val cantidad = productoConCantidad.cantidad
            total += precio * cantidad
        }
        var totalFormateado = String.format("%.2f", total)
        _presupuesto.value = totalFormateado.toDouble()
    }
    fun setPresupuestoVacio(){
        _presupuesto.value = 0.0
        _productosSeleccionados.value = emptyList()
    }
    fun guardarPresupuesto(usuarioViewModel: UsuarioViewModel) {
        val valorPresupuesto: Double = presupuesto.value ?: 0.0
        val mailUsuario = usuarioViewModel.mail.value.toString()

        if (mailUsuario != null) {
            val presupuestoModel = PresupuestoModel(
                items = productosSeleccionados.value.orEmpty().map { productoConCantidad ->
                    ProductoParaPresupuesto(
                        producto = productoConCantidad.producto,
                        cantidad = productoConCantidad.cantidad,
                        precioUnitario = productoConCantidad.producto.precio,
                        total = valorPresupuesto
                    )
                }.toCollection(ArrayList()),
                importeTotal = presupuesto.value ?: 0.0,
                mail = mailUsuario
            )

            viewModelScope.launch {
                try {
                    val response = service.guardarPresupuesto(presupuestoModel)
                    if (response.isSuccessful) {
                        Log.d("guardarPresupuesto", response.message())
                    } else {
                        Log.e("guardarPresupuesto", "Error al subir el presupuesto. Código: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("guardarPresupuesto", "Excepción al subir el presupuesto: ${e.message}")
                }
            }
        } else {
            // Manejar el caso en que el correo electrónico sea nulo
            Log.e("guardarPresupuesto", "El correo electrónico del usuario es nulo. No se puede guardar el presupuesto.")
            // También puedes mostrar un mensaje al usuario si es necesario
        }
    }
}
data class ProductoConCantidad(val producto: Producto, val cantidad: Int)
data class ProductoParaPresupuesto(val producto: Producto, val cantidad: Int, val precioUnitario: String, val total: Double)