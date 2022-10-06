package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class SavePointUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun savePoint(point: Point) {
        repository.savePoint(point)
    }
}