package com.kkalfas.sample.posts.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: PostsRepository
) : UseCase<Unit, List<Post>> {

    override suspend fun invoke(params: Unit): Either<Failure, List<Post>> {
        return repository.getAllPosts()
    }
}