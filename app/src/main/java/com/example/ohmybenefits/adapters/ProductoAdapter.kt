package com.example.ohmybenefits.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.ui.fragments.HomeFragmentDirections
import com.squareup.picasso.Picasso

class ProductoAdapter(
    private val navController: NavController
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    var productList: List<ProductoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productList[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun submitList(newList: List<ProductoModel>) {
        productList = newList
        notifyDataSetChanged()
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoria: TextView = itemView.findViewById(R.id.categoria)
        private val nombre: TextView = itemView.findViewById(R.id.nombre)
        private val precio: TextView = itemView.findViewById(R.id.precio)
        private val productImage: ImageView = itemView.findViewById(R.id.image_prod)
        private val detalle: Button = itemView.findViewById(R.id.detalle)

        fun bind(producto: ProductoModel) {
            val precioSigno = "$" + producto.precio
            categoria.text = producto.categorias[0]
            nombre.text = producto.nombre
            precio.text = precioSigno
            Picasso.get()
                .load(producto.imageUrl)
                .into(productImage)

            detalle.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToDetalle()
                navController.navigate(action)
            }
        }
    }
}