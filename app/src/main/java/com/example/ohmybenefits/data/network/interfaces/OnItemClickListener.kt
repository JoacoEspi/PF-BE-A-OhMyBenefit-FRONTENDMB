package com.example.ohmybenefits.data.network.interfaces

import com.example.ohmybenefits.data.model.Recomendacion

interface OnItemClickListener {
    fun onItemClick(recomendacionValues: Recomendacion)
}