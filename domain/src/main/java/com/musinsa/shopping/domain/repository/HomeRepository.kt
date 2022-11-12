package com.musinsa.shopping.domain.repository

import com.musinsa.shopping.domain.Resource
import com.musinsa.shopping.domain.model.remote.HomeContents

interface HomeRepository {
    suspend fun fetchHomeContents(): Resource<HomeContents>
}