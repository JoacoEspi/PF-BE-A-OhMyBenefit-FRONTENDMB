package com.example.ohmybenefits.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.R
import com.example.ohmybenefits.data.model.Lugar

class ComercioAdapter(private val comercios: List<Lugar>) : RecyclerView.Adapter<ComercioAdapter.ComercioViewHolder>() {
    class ComercioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreComercio)
        val direccionTextView: TextView = itemView.findViewById(R.id.direccionComercio)
        val distanciaTextView: TextView = itemView.findViewById(R.id.distanciaComercio)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComercioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comercio, parent, false)
        return ComercioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComercioViewHolder, position: Int) {
        val comercio = comercios[position]
        holder.nombreTextView.text = comercio.nombre
        holder.direccionTextView.text = comercio.direccion
        holder.distanciaTextView.text = comercio.distancia.toString()
    }

    override fun getItemCount(): Int {
        return comercios.size
    }
}