package com.kkalfas.sample.users.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase

class GetUser(
    private val repository: UserRepository
) : UseCase<GetUser.Params, User> {

    override suspend fun invoke(params: Params): Either<Failure, User> {
        return repository.getUserByI(params.userId)
    }

    data class Params(val userId: Int)
}