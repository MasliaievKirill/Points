package com.masliaiev.points.domain.repository

import androidx.paging.PagingData
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.helpers.Response
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun checkUserSignedIn(): Boolean

    suspend fun logIn(login: String, password: String): Response

    suspend fun logOut()

    suspend fun signUp(user: User): Response

    suspend fun getUser(userLogin: String): User

    suspend fun getUserLogin(): String

    fun getPointsPagingList(): Flow<PagingData<Point>>

    fun getPointsList(): Flow<List<Point>>

    suspend fun savePoint(point: Point)

    fun deletePoint(pointId: Int): Response
}