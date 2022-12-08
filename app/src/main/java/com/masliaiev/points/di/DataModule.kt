package com.masliaiev.points.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.masliaiev.points.data.database.AppDao
import com.masliaiev.points.data.database.AppDatabase
import com.masliaiev.points.data.network.ApiFactory
import com.masliaiev.points.data.network.ApiService
import com.masliaiev.points.data.repository.AppRepositoryImpl
import com.masliaiev.points.domain.repository.AppRepository
import com.masliaiev.points.helpers.AppConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository

    companion object {

        @Provides
        @Singleton
        fun provideAppDao(application: Application): AppDao {
            return AppDatabase.getInstance(application).appDao()
        }

        @Provides
        @Singleton
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Provides
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)
        }

    }
}