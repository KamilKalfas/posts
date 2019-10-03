package com.kkalfas.sample.users.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface UserRepository {
    suspend fun getUserByI(userId: Int): Either<Failure, User>

    class Impl(
        private val factory: UserDataSource.Factory
    ) : UserRepository {

        override suspend fun getUserByI(userId: Int): Either<Failure, User> {
            return factory.create().getUser(userId)
        }
    }
}