package com.masliaiev.points.presentation.screens.map

import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.entity.Point

data class AppMapScreenState(
    val showMapIsPermitted: Boolean = false,
    val userLogin: String = "",
    val currentCoordinates: LatLng = LatLng(0.0, 0.0),
    val pointsList: List<Point> = emptyList()
)
