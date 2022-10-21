package com.masliaiev.points.presentation.screens.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.usecases.GetPointsListUseCase
import com.masliaiev.points.domain.usecases.GetUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppMapViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsListUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase
) : ViewModel() {

    var showCurrentPosition = MutableSharedFlow<Unit>()
        private set

    var screenState by mutableStateOf(AppMapScreenState())
        private set

    init {
        getUserLogin()
        getPointsList()
    }

    private fun getUserLogin() {
        viewModelScope.launch {
            val userLogin = getUserLoginUseCase.getUserLogin()
            screenState = screenState.copy(userLogin = userLogin)
        }
    }

    private fun getPointsList() {
        viewModelScope.launch {
            getPointsUseCase.getPointsList().collect {
                screenState = screenState.copy(pointsList = it)
            }
        }
    }

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

    fun showCurrentPosition() {
        viewModelScope.launch {
            showCurrentPosition.emit(Unit)
        }
    }

}