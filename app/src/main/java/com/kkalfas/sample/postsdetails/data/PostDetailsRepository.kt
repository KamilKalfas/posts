package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface PostDetailsRepository {
    suspend fun getPostDetails(postId: Int, userId: Int): Either<Failure, PostDetails>

    class Impl(
        factory: PostDetailsDataSource.Factory
    ) : PostDetailsRepository {

        private val dataSource by lazy { factory.create() }

        override suspend fun getPostDetails(postId: Int, userId: Int): Either<Failure, PostDetails> {
            return dataSource.getPostDetails(postId = postId, userId = userId)
        }
    }
}