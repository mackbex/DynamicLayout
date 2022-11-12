package com.musinsa.shopping.data.source.remote

import com.musinsa.shopping.data.api.remote.HomeService
import com.musinsa.shopping.data.getRemoteResult
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeService: HomeService
)  {
    suspend fun fetchHomeContents() = getRemoteResult {
        homeService.fetchHomeContents()
    }
}