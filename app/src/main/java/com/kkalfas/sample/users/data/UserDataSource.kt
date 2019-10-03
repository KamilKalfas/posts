package com.kkalfas.sample.users.data

import com.kkalfas.sample.application.DatabaseFailure
import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao

interface UserDataSource {
    interface Factory {
        suspend fun create(): UserDataSource

        class Impl(
            private val cloudDataSource: UserDataSource,
            private val databaseDataSource: UserDataSource,
            private val cacheManager: CacheManager
        ) : Factory {
            override suspend fun create(): UserDataSource {
                return if (cacheManager.hasUsersSavedInDb()) databaseDataSource
                else cloudDataSource
            }
        }
    }

    suspend fun getUser(userId: Int): Either<Failure, User>

    class Cloud(
        private val networkService: NetworkService,
        private val postsDao: PostsDao,
        private val cacheManager: CacheManager
    ) : UserDataSource {
        override suspend fun getUser(userId: Int): Either<Failure, User> {
            return when (val result = networkService.getUser(userId)) {
                is Either.Left -> result
                is Either.Right -> {
                    if (!cacheManager.hasUserSavedInDb(result.b.id)) {
                        postsDao.insertUser(user = result.b)
                    }
                    result
                }
            }
        }
    }

    class Db(
        private val postsDao: PostsDao
    ) : UserDataSource {
        override suspend fun getUser(userId: Int): Either<Failure, User> {
            val user = postsDao.getUser(userId)
            return if (user == null) Either.Left(DatabaseFailure)
            else Either.Right(user)
        }
    }
}