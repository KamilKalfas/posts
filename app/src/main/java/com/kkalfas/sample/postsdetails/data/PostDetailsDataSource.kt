package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.application.DatabaseFailure
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao

interface PostDetailsDataSource {
    suspend fun getPostDetails(postId: Int, userId: Int): Either<Failure, PostDetails>

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
        private val networkService: NetworkService,
        private val postsDao: PostsDao,
        private val postDetailsMapper: PostDetailsMapper
    ) : PostDetailsDataSource {
        override suspend fun getPostDetails(
            postId: Int,
            userId: Int
        ): Either<Failure, PostDetails> {
            val comments = networkService.getComments(postId)
            return try {
                val post = postsDao.getPost(postId)
                val user = postsDao.getUser(userId)
                postDetailsMapper.map(comments, post, user)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Left(DatabaseFailure)
            }
        }
    }
}