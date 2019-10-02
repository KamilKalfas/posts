package com.kkalfas.sample.core

import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.postsdetails.data.Comment
import com.kkalfas.sample.users.data.User

interface NetworkService {
    suspend fun getPosts(): Either<Failure, List<Post>>
    suspend fun getUser(userId: Int): Either<Failure, User>
    suspend fun getComments(postId: Int): Either<Failure, List<Comment>>

    class Impl(
        private val api: Api
    ) : NetworkService {

        override suspend fun getPosts(): Either<Failure, List<Post>> {
            return execute { api.getPostsAsync() }
        }

        override suspend fun getUser(userId: Int): Either<Failure, User> {
            return execute { api.getUserAsync(userId) }
        }

        override suspend fun getComments(postId: Int): Either<Failure, List<Comment>> {
            return execute { api.getCommentsAsync(postId) }
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