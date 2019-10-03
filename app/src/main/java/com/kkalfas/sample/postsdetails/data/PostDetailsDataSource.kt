package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.application.DatabaseFailure
import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao

interface PostDetailsDataSource {
    suspend fun getPostDetails(postId: Int, userId: Int): Either<Failure, PostDetails>

    interface Factory {
        suspend fun create(): PostDetailsDataSource

        class Impl(
            private val cloudDataSource: PostDetailsDataSource,
            private val dbDataSource: PostDetailsDataSource,
            private val cacheManager: CacheManager
        ) : Factory {
            override suspend fun create(): PostDetailsDataSource {
                return if (cacheManager.hasCommentsSavedInDb()) dbDataSource
                else cloudDataSource
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
            return try {
                val user = postsDao.getUser(userId)
                if (user == null) Either.Left(DatabaseFailure)
                else {
                    val post = postsDao.getPost(postId)
                    val dbComments = postsDao.getComments(postId)
                    val comments = if (dbComments.isEmpty()) networkService.getComments(postId)
                    else Either.Right(dbComments)
                    postDetailsMapper.map(comments, post, user)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Left(DatabaseFailure)
            }
        }
    }

    class Db(
        private val postsDao: PostsDao,
        private val postDetailsMapper: PostDetailsMapper
    ) : PostDetailsDataSource {
        override suspend fun getPostDetails(
            postId: Int,
            userId: Int
        ): Either<Failure, PostDetails> {
            return try {
                val user = postsDao.getUser(userId)
                if (user == null) Either.Left(DatabaseFailure)
                else {
                    val post = postsDao.getPost(postId)
                    val comments = postsDao.getComments(postId)
                    postDetailsMapper.map(comments, post, user)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Left(DatabaseFailure)
            }
        }
    }
}