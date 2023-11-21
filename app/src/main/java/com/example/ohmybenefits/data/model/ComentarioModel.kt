package com.example.ohmybenefits.data.model

import com.google.gson.annotations.SerializedName
data class ComentarioModel(
   @SerializedName("_id")  val id: String? = null,
    @SerializedName("comentario")  val comentario: String?,
    @SerializedName("score")  val score: Double = 0.0,
    @SerializedName("magnitud")  val magnitud: Double = 0.0

)

