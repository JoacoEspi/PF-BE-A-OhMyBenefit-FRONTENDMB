package com.example.ohmybenefits.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmybenefits.data.model.ComentarioModel
import com.example.ohmybenefits.databinding.ItemComentarioBinding

class ComentarioAdapter(private var comentarios: List<ComentarioModel>) :
    RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {
    class ComentarioViewHolder(private val binding: ItemComentarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comentario: ComentarioModel) {
            binding.apply {
                analisisTextView.text = obtenerAnalisis(comentario.score)

                comentarioTextView.text = comentario.comentario

                val formattedScore = String.format("%.2f", comentario.score)
                val formattedMagnitud = String.format("%.2f", comentario.magnitud)

                scoreTextView.text = "Score: $formattedScore"
                magnitudTextView.text = "Magnitud: $formattedMagnitud"
            }
        }
        private fun obtenerAnalisis(score: Double): String { //*NUEVO
            return when {
                score < 0 -> "Negativo - Mejoraremos, Gracias!"
                score == 0.0 -> "Neutro - Gracias por el Comentario!"
                score > 0 -> "Positivo - Nos alegra servirte!"
                else -> "Analisis: Desconocido"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val binding = ItemComentarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComentarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }

    fun updateData(newComentarios: List<ComentarioModel>){
        Log.d("ComentarioAdapter", "updateData llamado")
        comentarios = newComentarios
        notifyDataSetChanged()
    }
}



