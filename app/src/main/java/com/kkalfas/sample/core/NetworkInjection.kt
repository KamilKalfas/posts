package com.kkalfas.sample.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @JvmStatic
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val baseUrl = "http://jsonplaceholder.typicode.com"
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @JvmStatic
    fun provideNetworkService(retrofit: Retrofit) : NetworkService {
        return NetworkService.Impl(retrofit.create(Api::class.java))
    }
}
