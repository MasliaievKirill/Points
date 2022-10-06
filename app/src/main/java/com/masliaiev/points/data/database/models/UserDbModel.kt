package com.masliaiev.points.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey
    val login: String,
    val password: String,
    val email: String
)
