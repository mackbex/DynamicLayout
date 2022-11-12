package com.musinsa.shopping.data.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.musinsa.shopping.data.BuildConfig
import com.musinsa.shopping.data.api.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(Level.BODY)
            client.addInterceptor(logging)
        }

        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient): Retrofit {

        val json = Json {
            coerceInputValues = true
            encodeDefaults = true
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_V1)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }


}
