package com.masliaiev.points.domain.usecases

import androidx.paging.PagingData
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPointsPagingListUseCase @Inject constructor(
    private val repository: AppRepository
) {
    fun getPointsPagingList(): Flow<PagingData<Point>> {
        return repository.getPointsPagingList()
    }
}