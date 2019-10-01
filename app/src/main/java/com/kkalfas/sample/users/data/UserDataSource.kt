package com.kkalfas.sample.users.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService

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
        private val networkService: NetworkService
    ) : UserDataSource {
        override suspend fun getUser(userId: Int): Either<Failure, User> {
            return networkService.getUser(userId)
        }
    }
}