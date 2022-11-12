package com.musinsa.shopping.data.api.remote

import com.musinsa.shopping.data.model.remote.HomeContentsResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("interview/list.json")
    suspend fun fetchHomeContents(): Response<HomeContentsResponse>
}