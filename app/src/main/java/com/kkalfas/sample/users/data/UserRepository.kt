package com.kkalfas.sample.users.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface UserRepository {
    suspend fun getUserByI(userId: Int): Either<Failure, User>

    class Impl(
        factory: UserDataSource.Factory
    ) : UserRepository {
        private val dataSource by lazy { factory.create() }

        override suspend fun getUserByI(userId: Int): Either<Failure, User> {
            return dataSource.getUser(userId)
        }
    }
}