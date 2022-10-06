package com.masliaiev.points.presentation.screens.map

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.usecases.GetPointsListUseCase
import com.masliaiev.points.domain.usecases.GetUserLoginUseCase
import com.masliaiev.points.domain.usecases.SavePointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppMapViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsListUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase,
    private val savePointUseCase: SavePointUseCase
) : ViewModel() {

    var showMap by mutableStateOf(false)
    var userLogin by mutableStateOf("")
    var currentCoordinates by mutableStateOf(LatLng(0.0, 0.0))
    var pointsList by mutableStateOf(flowOf<List<Point>>())

    init {
        getUserLogin()
        getPointsList()
    }

    private fun getUserLogin() {
        viewModelScope.launch {
            userLogin = getUserLoginUseCase.getUserLogin()
        }
    }

    private fun getPointsList() {
        pointsList = getPointsUseCase.getPointsList()
    }

    fun setUserCurrentCoordinates(latLng: LatLng) {
        currentCoordinates = latLng
    }

    fun checkPermissions(permissions: Map<String, Boolean>) {
        var isGranted = false
        permissions.entries.forEach {
            isGranted = it.value
        }
        if (isGranted) {
            showMap = true
        }
    }

    fun savePoint(point: Point) {
        viewModelScope.launch {
            savePointUseCase.savePoint(point)
        }
    }

}