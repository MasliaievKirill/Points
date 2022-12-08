package com.masliaiev.points.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DirectionsResultDto(
    @SerializedName("routes")
    @Expose
    val routes: List<RoutesDto>,
    @SerializedName("status")
    @Expose
    val status: String
)