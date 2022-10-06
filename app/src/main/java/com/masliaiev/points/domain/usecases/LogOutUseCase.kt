package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.repository.AppRepository
import com.masliaiev.points.helpers.Response
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend fun logOut() {
        repository.logOut()
    }
}