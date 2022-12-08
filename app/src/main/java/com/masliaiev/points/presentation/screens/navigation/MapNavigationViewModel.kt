package com.masliaiev.points.presentation.screens.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.usecases.GetRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapNavigationViewModel @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase
) : ViewModel() {

    var showToast = MutableSharedFlow<String>()
        private set

    var screenState by mutableStateOf(MapNavigationScreenState())
        private set

    fun setUserCurrentCoordinates(latLng: LatLng) {
        screenState = screenState.copy(currentCoordinates = latLng)
    }

    fun checkPermissions(permissions: Map<String, Boolean>) {
        var isGranted = false
        permissions.entries.forEach {
            isGranted = it.value
        }
        if (isGranted) {
            screenState = screenState.copy(showMapIsPermitted = true)
        }
    }

    fun getRoute(startCoordinates: LatLng, endCoordinates: LatLng) {
        viewModelScope.launch {
            val route = getRouteUseCase.getRoute(startCoordinates, endCoordinates)
            if (route != null) {
                screenState = screenState.copy(route = route)
            } else {
                showToast.emit("No route")
            }
        }
    }

}