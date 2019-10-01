package com.kkalfas.sample.core

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit {
        val baseUrl = "http://jsonplaceholder.typicode.com"
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @JvmStatic
    fun provideNetworkService(retrofit: Retrofit) : NetworkService {
        return NetworkService.Impl(retrofit.create(Api::class.java))
    }
}

