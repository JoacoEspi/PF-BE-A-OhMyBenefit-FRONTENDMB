package com.example.ohmybenefits.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.databinding.ItemProductoPresupuestoBinding
import com.example.ohmybenefits.ui.viewmodel.ProductoParaPresupuesto

class PresupuestoAdapter : RecyclerView.Adapter<PresupuestoAdapter.ProductoViewHolder>() {
    private var productos: List<ProductoParaPresupuesto> = emptyList()
    fun actualizarLista(nuevaLista: List<ProductoParaPresupuesto>) {
        productos = nuevaLista
        Log.e("PresupuestoAdapter", "Lista actualizada: $productos")
    }
    fun limpiarLista(){
        val itemCount = productos.size
        productos = emptyList()
        notifyDataSetChanged()
        notifyItemRangeRemoved(0, itemCount)
        Log.e("PresupuestoAdapter", "Lista actualizada: $productos")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoPresupuestoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    class ProductoViewHolder(private val binding: ItemProductoPresupuestoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: ProductoParaPresupuesto) {
            binding.textNombreProductoPresupuesto.text = producto.producto.nombre
            binding.textCantidad.text = "Cantidad: ${producto.cantidad}"

            val precioString = producto.producto.precio.trim()
            val precioLimpio = precioString.replace("[^\\d.,]".toRegex(), "")
            val precioLimpioPunto = precioLimpio.replace(",", ".")
            var precio = precioLimpioPunto.toDouble()

            binding.textPrecioUnitario.text = "Precio Unitario: $$precio"
            binding.textPrecioTotal.text = "Precio Total: $${producto.cantidad * precio}"

            val nombreSupermercado = when (producto.producto.idComercio) {
                "1" -> "Coto"
                "2" -> "Carrefour"
                "3" -> "Dia"
                else -> "Desconocido"
            }
            binding.textSuper.text = "Super: $nombreSupermercado"
        }
    }

}
