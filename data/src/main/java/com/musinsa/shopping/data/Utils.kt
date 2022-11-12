package com.musinsa.shopping.data

import com.musinsa.shopping.domain.model.remote.ApiFailure
import com.musinsa.shopping.domain.Resource
import retrofit2.Response

inline fun <reified T: Any, reified R: Any> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> try {
            Resource.Success(transform(data))
        } catch (e:Exception) {
            Resource.Failure(ApiFailure(msg = e.message))
        }
        is Resource.Failure -> Resource.Failure(this.apiFailure)
        is Resource.Loading -> Resource.Loading
    }
}

suspend inline fun <T> getRemoteResult(crossinline call: suspend () -> Response<T>): Resource<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Resource.Success(body)
        }
        return error(ApiFailure(msg = "Failed to load data."))
    } catch (e: Exception) {
        return error(ApiFailure(e.message ?: e.toString()))
    }
}

fun <T> error(apiFailure: ApiFailure): Resource<T> {
    return Resource.Failure(apiFailure)
}
