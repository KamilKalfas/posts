package com.kkalfas.sample.core

import com.kkalfas.sample.database.PostsDao

interface CacheManager {
    suspend fun hasPostsSavedInDb(): Boolean
    suspend fun hasUserSavedInDb(userId: Int): Boolean
    suspend fun hasUsersSavedInDb(): Boolean
    suspend fun hasCommentsSavedInDb(): Boolean

    class Impl(
        private val postsDao: PostsDao
    ) : CacheManager {

        override suspend fun hasPostsSavedInDb(): Boolean {
            return postsDao.postsCount() == 100
        }

        override suspend fun hasUserSavedInDb(userId: Int): Boolean {
            return postsDao.getUser(userId) != null
        }

        override suspend fun hasCommentsSavedInDb(): Boolean {
            return postsDao.commentsCount() > 0
        }

        override suspend fun hasUsersSavedInDb(): Boolean {
            return postsDao.userCount() == 10
        }
    }
}