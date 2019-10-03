package com.kkalfas.sample.posts.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface PostsRepository {
    suspend fun getAllPosts(): Either<Failure, List<Post>>

    class Impl(
        private val factory: PostsDataSource.Factory
    ) : PostsRepository {

        override suspend fun getAllPosts(): Either<Failure, List<Post>> {
            return factory.create().getPosts()
        }
    }
}