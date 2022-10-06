package com.masliaiev.points.data.mapper

import com.masliaiev.points.data.database.models.PointDbModel
import com.masliaiev.points.data.database.models.UserDbModel
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.entity.User
import javax.inject.Inject

class AppMapper @Inject constructor() {

    fun mapUserEntityToUserDbModel(user: User): UserDbModel {
        return UserDbModel(
            login = user.login,
            password = user.password,
            email = user.email
        )
    }

    fun mapUserDbModelToUserEntity(userDbModel: UserDbModel): User {
        return User(
            login = userDbModel.login,
            password = userDbModel.password,
            email = userDbModel.email
        )
    }

    fun mapPointEntityToPointDbModel(point: Point): PointDbModel {
        return PointDbModel(
            id = UNDEFINED_ID,
            name = point.name,
            latitude = point.latitude,
            longitude = point.longitude,
            userLogin = point.userLogin
        )
    }

    fun mapPointDbModelToPointEntity(pointDbModel: PointDbModel): Point {
        return Point(
            name = pointDbModel.name,
            latitude = pointDbModel.latitude,
            longitude = pointDbModel.longitude,
            userLogin = pointDbModel.userLogin
        )
    }

    companion object {
        private const val UNDEFINED_ID = 0
    }

}