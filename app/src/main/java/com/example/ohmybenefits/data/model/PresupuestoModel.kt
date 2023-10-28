package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName

data class PresupuestoModel(
    @SerializedName("_id") val _id: String,
    @SerializedName("items") val items: ArrayList<Any>,
    @SerializedName("importeTotal") val importeTotal: Number,
    @SerializedName("usuario") val usuario: String
)