package com.example.ohmybenefits.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.data.model.ProductoDetalleModel
import com.example.ohmybenefits.data.model.Recomendacion
import com.example.ohmybenefits.data.network.interfaces.OnItemClickListener
import com.example.ohmybenefits.databinding.ItemProductoRecomendadoBinding
import com.squareup.picasso.Picasso

class RecomendedProductAdapter(
    private val productoDetalleModel: ProductoDetalleModel,
    private val itemClickListener: OnItemClickListener,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RecomendedProductAdapter.ProductoRecomendadoViewHolder>() {

    inner class ProductoRecomendadoViewHolder(private val binding: ItemProductoRecomendadoBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val posicion = adapterPosition
            if (posicion != RecyclerView.NO_POSITION) {
                val recomendacion = productoDetalleModel.recomendaciones.recomms[posicion]
                itemClickListener.onItemClick(recomendacion)
            }
        }

        fun bind(recomendacion: Recomendacion) {
            // Usar binding para establecer valores en las vistas
            val recomendacionValues = recomendacion.values
            binding.textNombreProductoRecomendado.text = recomendacionValues.nombre
            binding.textPrecioProductoRecomendado.text = "Precio: ${recomendacionValues.precio}"
            Picasso.get().load(recomendacionValues.imageUrl.firstOrNull()).into(binding.imageProductoRecomendado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoRecomendadoViewHolder {
        val binding = ItemProductoRecomendadoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoRecomendadoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoRecomendadoViewHolder, position: Int) {
        val recomendacion = productoDetalleModel.recomendaciones.recomms[position]
        holder.bind(recomendacion)
    }

    override fun getItemCount(): Int = productoDetalleModel.recomendaciones.recomms.size
}