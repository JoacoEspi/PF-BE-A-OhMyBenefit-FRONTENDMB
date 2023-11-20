package com.example.ohmybenefits.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.core.SharedPreferences
import com.example.ohmybenefits.data.model.ProductoModel
import com.example.ohmybenefits.ui.fragments.DetalleFragment
import com.squareup.picasso.Picasso

class ProductoAdapter(private val context: Context) :
    PagingDataAdapter<ProductoModel, ProductoAdapter.ProductoViewHolder>(PRODUCTO_COMPARATOR) {

    private var listaProductos: ArrayList<ProductoModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListaProducto(listaProducto: List<ProductoModel>) {
        this.listaProductos.clear()
        this.listaProductos.addAll(listaProducto)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoria: TextView = itemView.findViewById(R.id.categoria)
        private val nombre: TextView = itemView.findViewById(R.id.nombre)
        private val precio: TextView = itemView.findViewById(R.id.precio)
        private val productImage: ImageView = itemView.findViewById(R.id.image_prod)
        private val prodFavoriteToggle: ToggleButton = itemView.findViewById(R.id.prod_fav)
        private val detalle: Button = itemView.findViewById(R.id.detalle)

        fun bind(producto: ProductoModel) {
            val prefs = SharedPreferences(context)
            val isFav = prefs.isFavoriteProduct(producto._id)
            val precioSigno = "$"+producto.precio
            categoria.text = producto.categorias[0]
            nombre.text = producto.nombre
            precio.text = precioSigno
            Picasso.get()
                .load(producto.imageUrl)
                .into(productImage)

            prodFavoriteToggle.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_toggle_bg
            );
            prodFavoriteToggle.isChecked = isFav

            prodFavoriteToggle.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    prefs.addFavoriteProduct(producto._id)
                    prodFavoriteToggle.isChecked = isChecked
                    prodFavoriteToggle.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_toggle_bg
                    )
                } else {
                    prefs.removeFavoriteProduct(producto._id)
                    prodFavoriteToggle.isChecked = isChecked
                    prodFavoriteToggle.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_toggle
                    )
                }
            }

            detalle.setOnClickListener {
                val intent = Intent(context, DetalleFragment::class.java)
                intent.putExtra("idProduct", producto._id)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        private val PRODUCTO_COMPARATOR = object : DiffUtil.ItemCallback<ProductoModel>() {
            override fun areItemsTheSame(oldItem: ProductoModel, newItem: ProductoModel) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: ProductoModel, newItem: ProductoModel) =
                oldItem == newItem
        }
    }
    }