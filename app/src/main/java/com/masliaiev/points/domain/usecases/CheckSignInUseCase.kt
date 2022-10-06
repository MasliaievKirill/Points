package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class CheckSignInUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend fun checkUserSignedIn(): Boolean {
        return repository.checkUserSignedIn()
    }
}