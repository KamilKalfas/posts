package com.kkalfas.sample.posts.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao

interface PostsDataSource {
    suspend fun getPosts(): Either<Failure, List<Post>>

    interface Factory {
        fun create(): PostsDataSource

        class Impl(
            private val cloudDataSource: PostsDataSource
        ) : Factory {

            override fun create(): PostsDataSource {
                return cloudDataSource
            }
        }
    }

    class Cloud(
        private val networkService: NetworkService,
        private val postsDao: PostsDao
    ) : PostsDataSource {

        override suspend fun getPosts(): Either<Failure, List<Post>> {
            return when (val result = networkService.getPosts()) {
                is Either.Left -> result
                is Either.Right -> {
                    postsDao.insertPosts(posts = result.b)
                    result
                }
            }
        }
    }
}