package com.masliaiev.points.presentation.screens.add_point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.usecases.SavePointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPointScreenViewModel @Inject constructor(
    private val savePointUseCase: SavePointUseCase
) : ViewModel() {


    fun savePoint(point: Point) {
        viewModelScope.launch {
            savePointUseCase.savePoint(point)
        }
    }
}