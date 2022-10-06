package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.repository.AppRepository
import com.masliaiev.points.helpers.Response
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend fun signUp(user: User): Response {
        return repository.signUp(user)
    }
}