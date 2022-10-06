package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPointsListUseCase @Inject constructor(
    private val repository: AppRepository
) {
    fun getPointsList(): Flow<List<Point>> {
        return repository.getPointsList()
    }
}