package com.musinsa.shopping.data

import com.musinsa.shopping.domain.model.remote.ApiFailure
import com.musinsa.shopping.domain.Resource
import okhttp3.OkHttpClient
import retrofit2.Response
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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

fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
    val naiveTrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
    }

    val insecureSocketFactory = SSLContext.getInstance("SSL").apply {
        val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
        init(null, trustAllCerts, SecureRandom())
    }.socketFactory

    sslSocketFactory(insecureSocketFactory, naiveTrustManager)
    hostnameVerifier(HostnameVerifier { _, _ -> true })
    return this
}
