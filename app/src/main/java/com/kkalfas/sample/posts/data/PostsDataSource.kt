package com.kkalfas.sample.posts.data

import com.kkalfas.sample.application.DatabaseFailure
import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao
import java.lang.Exception

interface PostsDataSource {
    suspend fun getPosts(): Either<Failure, List<Post>>

    interface Factory {
        suspend fun create(): PostsDataSource

        class Impl(
            private val cloudDataSource: PostsDataSource,
            private val databaseDataSource: PostsDataSource,
            private val cacheManager: CacheManager
        ) : Factory {

            override suspend fun create(): PostsDataSource {
                return if (cacheManager.hasPostsSavedInDb()) databaseDataSource
                else cloudDataSource
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

    class Db(
        private val postsDao: PostsDao
    ) : PostsDataSource {

        override suspend fun getPosts(): Either<Failure, List<Post>> {
            return try {
                Either.Right(postsDao.getPosts())
            } catch (e: Exception) {
                Either.Left(DatabaseFailure)
            }
        }
    }
}