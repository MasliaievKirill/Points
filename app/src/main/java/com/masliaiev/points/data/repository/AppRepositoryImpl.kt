package com.masliaiev.points.data.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.masliaiev.points.BuildConfig
import com.masliaiev.points.data.database.AppDao
import com.masliaiev.points.data.database.models.UserDbModel
import com.masliaiev.points.data.mapper.AppMapper
import com.masliaiev.points.data.network.ApiService
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.repository.AppRepository
import com.masliaiev.points.helpers.AppConstants
import com.masliaiev.points.helpers.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val apiService: ApiService,
    private val appMapper: AppMapper,
    private val sharedPreferences: SharedPreferences
) : AppRepository {

    override suspend fun checkUserSignedIn(): Boolean {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getBoolean(AppConstants.KEY_SIGNED_IN, false)
        }
    }

    override suspend fun logIn(login: String, password: String): Response {
        val dbResponse = appDao.logIn(login = login, password = password)
        return withContext(Dispatchers.IO) {
            dbResponse?.let {
                sharedPreferences.edit().putBoolean(AppConstants.KEY_SIGNED_IN, true).apply()
                sharedPreferences.edit().putString(AppConstants.KEY_USER_LOGIN, login).apply()
                Response.Success
            } ?: Response.Error("Wrong login or password")
        }
    }

    override suspend fun logOut() {
        return withContext(Dispatchers.IO) {
            sharedPreferences.edit().putBoolean(AppConstants.KEY_SIGNED_IN, false).apply()
        }
    }

    override suspend fun signUp(user: User): Response {
        val dbResponse = appDao.createUser(
            appMapper.mapUserEntityToUserDbModel(user)
        )
        return withContext(Dispatchers.IO) {
            if (dbResponse != -1L) {
                sharedPreferences.edit().putBoolean(AppConstants.KEY_SIGNED_IN, true).apply()
                sharedPreferences.edit().putString(AppConstants.KEY_USER_LOGIN, user.login).apply()
                Response.Success
            } else {
                Response.Error("Account already exist")
            }
        }
    }

    override suspend fun getUser(userLogin: String): User {
        return appMapper.mapUserDbModelToUserEntity(
            appDao.getUser(userLogin) ?: UserDbModel("unknown", "unknown", "unknown")
        )
    }

    override suspend fun getUserLogin(): String {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getString(AppConstants.KEY_USER_LOGIN, "") ?: ""
        }
    }

    override fun getPointsPagingList(): Flow<PagingData<Point>> {
        val userLogin = sharedPreferences.getString(AppConstants.KEY_USER_LOGIN, "") ?: ""
        return Pager(
            PagingConfig(
                pageSize = MAX_NUMBER_OF_ITEMS_LOADED_AT_ONCE,
                enablePlaceholders = true,
                maxSize = MAX_NUMBER_OF_ITEMS
            )
        ) {
            appDao.getPointsPagingList(userLogin)
        }.flow.map {
            it.map { pointDbModel ->
                appMapper.mapPointDbModelToPointEntity(pointDbModel)
            }
        }
    }

    override fun getPointsList(): Flow<List<Point>> {
        val userLogin = sharedPreferences.getString(AppConstants.KEY_USER_LOGIN, "") ?: ""
        return appDao.getPointsList(userLogin).map {
            it.map { pointDbModel ->
                appMapper.mapPointDbModelToPointEntity(pointDbModel)
            }
        }
    }

    override suspend fun savePoint(point: Point) {
        appDao.insertPoint(appMapper.mapPointEntityToPointDbModel(point))
    }

    override fun deletePoint(pointId: Int): Response {
        TODO("Not yet implemented")
    }

    override suspend fun getRoute(startCoordinates: LatLng, endCoordinates: LatLng): List<LatLng>? {
        val points = apiService.getDirections(
            "${startCoordinates.latitude},${startCoordinates.longitude}",
            "${endCoordinates.latitude},${endCoordinates.longitude}",
            BuildConfig.MAPS_API_KEY
        ).routes.firstOrNull()?.overviewPolyline?.points
        return points?.let { PolyUtil.decode(points) }
    }

    companion object {
        private const val MAX_NUMBER_OF_ITEMS_LOADED_AT_ONCE = 10
        private const val MAX_NUMBER_OF_ITEMS = 100
    }
}