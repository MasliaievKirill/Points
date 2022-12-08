package com.masliaiev.points.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/maps/api/directions/json")
    suspend fun getDirections(
        @Query(QUERY_PARAM_ORIGIN) origin: String,
        @Query(QUERY_PARAM_DESTINATION) destination: String,
        @Query(QUERY_PARAM_KEY) key: String
    ): DirectionsResultDto

    companion object {
        private const val QUERY_PARAM_ORIGIN = "origin"
        private const val QUERY_PARAM_DESTINATION = "destination"
        private const val QUERY_PARAM_KEY = "key"
    }
}