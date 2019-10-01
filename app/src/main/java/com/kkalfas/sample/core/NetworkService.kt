package com.kkalfas.sample.core

import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    suspend fun getPosts(): Either<Failure, List<Post>>
    suspend fun getUser(userId: Int): Either<Failure, User>

    class Impl(
        private val api: Api
    ) : NetworkService {

        override suspend fun getPosts(): Either<Failure, List<Post>> {
            return execute { api.getPostsAsync() }
        }

        override suspend fun getUser(userId: Int): Either<Failure, User> {
            return execute { api.getUserAsync(userId) }
        }

        private inline fun <reified T : Any> execute(block: () -> T): Either<Failure, T> {
            return try {
                Either.Right(block())
            } catch (e: Exception) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}