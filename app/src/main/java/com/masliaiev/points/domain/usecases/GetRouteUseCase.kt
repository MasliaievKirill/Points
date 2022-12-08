package com.masliaiev.points.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun getRoute(startCoordinates: LatLng, endCoordinates: LatLng): List<LatLng>? {
        return repository.getRoute(startCoordinates, endCoordinates)
    }
}