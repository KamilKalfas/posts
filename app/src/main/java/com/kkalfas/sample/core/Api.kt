package com.kkalfas.sample.core

import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/posts")
    suspend fun getPostsAsync(): List<Post>

    @GET("/users/{userId}")
    suspend fun getUserAsync(@Path("userId") userId: Int) : User
}