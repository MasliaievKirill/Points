package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class DeletePointUseCase @Inject constructor(
    private val repository: AppRepository
) {
}