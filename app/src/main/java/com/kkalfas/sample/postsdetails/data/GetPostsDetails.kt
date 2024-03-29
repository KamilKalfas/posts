package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase

class GetPostsDetails(
    private val repository: PostDetailsRepository
) : UseCase<GetPostsDetails.Params, PostDetails> {

    override suspend fun invoke(params: Params): Either<Failure, PostDetails> {
        return repository.getPostDetails(
            postId = params.postId,
            userId = params.userId
        )
    }

    data class Params(val postId: Int, val userId: Int)
}