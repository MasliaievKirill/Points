package com.masliaiev.points.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PolylineDto(
    @SerializedName("points")
    @Expose
    val points: String
)
