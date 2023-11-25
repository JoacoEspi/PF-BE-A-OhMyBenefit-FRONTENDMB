package com.example.ohmybenefits.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.data.model.CategoriaModel

class CategoriaAdapter(
    private val context: Context,
    private val categorias: List<CategoriaModel>
) :
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoria: CategoriaModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    fun setCategorias(categorias: List<CategoriaModel>) {
        (this.categorias as ArrayList).clear()
        this.categorias.addAll(categorias)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoriaImageView: ImageView = itemView.findViewById(R.id.categoriaImageView)
        private val categoriaTextView: TextView = itemView.findViewById(R.id.categoriasTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(categorias[position])
                }
            }
        }

        fun bind(categoria: CategoriaModel) {
            categoriaImageView.setImageResource(categoria.imagen)
            categoriaTextView.text = categoria.nombre.toString()
        }
    }
}
