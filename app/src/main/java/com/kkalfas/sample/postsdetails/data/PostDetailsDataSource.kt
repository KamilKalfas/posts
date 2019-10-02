package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService

interface PostDetailsDataSource {
    suspend fun getPostDetails(userId: Int, postId: Int): Either<Failure, List<PostDetails>>

    interface Factory {
        fun create(): PostDetailsDataSource

        class Impl(
            private val dataSource: PostDetailsDataSource
        ) : Factory {
            override fun create(): PostDetailsDataSource {
                return dataSource
            }
        }
    }

    class Cloud(
        private val networkService: NetworkService
    ) : PostDetailsDataSource {
        override suspend fun getPostDetails(
            userId: Int,
            postId: Int
        ): Either<Failure, List<PostDetails>> {
            val comments = networkService.getComments(postId)
            return Either.Left(Failure.ServerError)
        }

    }

//    class Db constructor(
//
//    ) : PostDetailsDataSource {
//
//        override suspend fun getPostDetails(userId: Int, postId: Int): Either<Failure, List<PostDetails>> {
//
//        }
//    }
}