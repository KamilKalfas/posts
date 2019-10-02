package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure

interface PostDetailsRepository {
    suspend fun getPostDetails(postId: Int): Either<Failure, PostDetails>

    class Impl(
        factory: PostDetailsDataSource.Factory
    ) : PostDetailsRepository {

        private val dataSource by lazy { factory.create() }

        override suspend fun getPostDetails(postId: Int): Either<Failure, PostDetails> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}