package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface PostDetailsRepository {
    suspend fun getPostDetails(postId: Int, userId: Int): Either<Failure, PostDetails>

    class Impl(
        private val factory: PostDetailsDataSource.Factory
    ) : PostDetailsRepository {

        override suspend fun getPostDetails(
            postId: Int,
            userId: Int
        ): Either<Failure, PostDetails> {
            return factory.create().getPostDetails(postId = postId, userId = userId)
        }
    }
}