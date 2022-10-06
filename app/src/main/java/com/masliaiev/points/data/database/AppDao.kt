package com.masliaiev.points.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.masliaiev.points.data.database.models.PointDbModel
import com.masliaiev.points.data.database.models.UserDbModel
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(user: UserDbModel): Long

    @Query("SELECT * FROM users WHERE login == :login AND password == :password")
    suspend fun logIn(login: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: PointDbModel)

    @Query("SELECT * FROM points WHERE userLogin == :userLogin")
    fun getPointsPagingList(userLogin: String): PagingSource<Int, PointDbModel>

    @Query("SELECT * FROM points WHERE userLogin == :userLogin")
    fun getPointsList(userLogin: String): Flow<List<PointDbModel>>

    @Query("SELECT * FROM points WHERE id == :pointId")
    suspend fun getPoint(pointId: Int): PointDbModel?

    @Query("SELECT * FROM users WHERE login == :userLogin")
    suspend fun getUser(userLogin: String): UserDbModel?

    @Delete
    suspend fun deletePoint(point: PointDbModel)

}