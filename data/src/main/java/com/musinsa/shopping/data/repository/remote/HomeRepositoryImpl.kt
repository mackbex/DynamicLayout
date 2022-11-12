package com.musinsa.shopping.data.repository.remote

import com.musinsa.shopping.data.map
import com.musinsa.shopping.data.source.remote.HomeRemoteDataSource
import com.musinsa.shopping.domain.Resource
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.domain.repository.HomeRepository
import com.musinsa.shopping.data.model.mapper.mapToDomain
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {

    override suspend fun fetchHomeContents(): Resource<HomeContents> {
        return homeRemoteDataSource.fetchHomeContents().map { homeContentsResponse ->
            homeContentsResponse.mapToDomain()
        }
    }
}