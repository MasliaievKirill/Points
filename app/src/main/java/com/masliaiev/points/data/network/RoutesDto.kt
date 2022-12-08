package com.masliaiev.points.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RoutesDto(
    @SerializedName("overview_polyline")
    @Expose
    val overviewPolyline: PolylineDto
)