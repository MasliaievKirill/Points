package com.masliaiev.points.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class PointDbModel(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val userLogin: String
)
