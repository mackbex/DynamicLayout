package com.musinsa.shopping.data.module

import com.musinsa.shopping.data.repository.remote.HomeRepositoryImpl
import com.musinsa.shopping.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bind(impl: HomeRepositoryImpl): HomeRepository
}

