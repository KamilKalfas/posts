package com.kkalfas.sample.posts.data

import com.kkalfas.sample.core.*

interface PostsRepository {
    suspend fun getAllPosts() : Either<Failure, List<Post>>

    class Impl(
        factory: PostsDataSource.Factory
    ) : PostsRepository {
        private val dataSource by lazy { factory.create() }

        override suspend fun getAllPosts(): Either<Failure, List<Post>> {
            return dataSource.getPosts()
        }
    }
}