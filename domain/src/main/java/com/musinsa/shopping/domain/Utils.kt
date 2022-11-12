package com.musinsa.shopping.domain

import com.musinsa.shopping.domain.model.remote.ApiFailure

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val apiFailure: ApiFailure) : Resource<Nothing>()
}