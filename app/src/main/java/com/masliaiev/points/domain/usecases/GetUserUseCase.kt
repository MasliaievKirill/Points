package com.masliaiev.points.domain.usecases

import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.repository.AppRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun getUser(userLogin: String): User {
        return repository.getUser(userLogin)
    }
}