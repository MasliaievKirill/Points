package com.masliaiev.points.presentation.screens.points_list

import androidx.lifecycle.ViewModel
import com.masliaiev.points.domain.usecases.GetPointsPagingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PointsListScreenViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsPagingListUseCase
) : ViewModel() {

    var pointsList = getPointsUseCase.getPointsPagingList()

}