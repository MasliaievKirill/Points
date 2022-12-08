package com.masliaiev.points.presentation.screens.navigation

import com.google.android.gms.maps.model.LatLng

data class MapNavigationScreenState(
    val showMapIsPermitted: Boolean = false,
    val currentCoordinates: LatLng = LatLng(0.0, 0.0),
    val route: List<LatLng> = mutableListOf()
)
