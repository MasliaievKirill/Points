package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.repository.AppRepository
import com.masliaiev.points.helpers.Response
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend fun logIn(login: String, password: String): Response {
        return repository.logIn(login, password)
    }
}