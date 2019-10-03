package com.kkalfas.sample.users.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao

interface UserDataSource {
    interface Factory {
        fun create(): UserDataSource

        class Impl(
            private val cloudDataSource: UserDataSource
        ) : Factory {
            override fun create(): UserDataSource {
                return cloudDataSource
            }
        }
    }

    suspend fun getUser(userId: Int): Either<Failure, User>

    class Cloud(
        private val networkService: NetworkService,
        private val postsDao: PostsDao
    ) : UserDataSource {
        override suspend fun getUser(userId: Int): Either<Failure, User> {
            return when (val result = networkService.getUser(userId)) {
                is Either.Left -> result
                is Either.Right -> {
                    postsDao.insertUser(user = result.b)
                    result
                }
            }
        }
    }
}