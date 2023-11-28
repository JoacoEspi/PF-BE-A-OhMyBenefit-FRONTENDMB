package com.example.ohmybenefits.data.model

import com.example.ohmybenefits.ui.viewmodel.ProductoParaPresupuesto
import com.google.gson.annotations.SerializedName

data class PresupuestoModel(
    @SerializedName("items") val items: ArrayList<ProductoParaPresupuesto>,
    @SerializedName("importeTotal") val importeTotal: Double,
    @SerializedName("mail") val mail: String?
)