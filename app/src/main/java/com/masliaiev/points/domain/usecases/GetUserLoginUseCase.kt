package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class GetUserLoginUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun getUserLogin(): String {
        return repository.getUserLogin()
    }
}