package com.kkalfas.sample.core

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.users.data.User
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CacheManagerTest : MockkTest() {

    private val postsDao: PostsDao = mockk()
    private val subject = CacheManager.Impl(postsDao)

    @Test
    fun `hasPostsSavedInDb when there is at least one post returns true`() {
        coEvery { postsDao.postsCount() } returns 100
        val result = runBlocking { subject.hasPostsSavedInDb() }

        coVerify { postsDao.postsCount() }
        assertThat(result).isTrue()
    }

    @Test
    fun `hasPostsSavedInDb when there is no posts returns false`() {
        coEvery { postsDao.postsCount() } returns 0
        val result = runBlocking { subject.hasPostsSavedInDb() }

        coVerify { postsDao.postsCount() }
        assertThat(result).isFalse()
    }

    @Test
    fun `hasCommentsSavedInDb when there is at least one comment returns true`() {
        coEvery { postsDao.commentsCount() } returns 1
        val result = runBlocking { subject.hasCommentsSavedInDb() }

        coVerify { postsDao.commentsCount() }
        assertThat(result).isTrue()
    }

    @Test
    fun `hasCommentsSavedInDb when there is no comment returns false`() {
        coEvery { postsDao.commentsCount() } returns 0
        val result = runBlocking { subject.hasCommentsSavedInDb() }

        coVerify { postsDao.commentsCount() }
        assertThat(result).isFalse()
    }

    @Test
    fun `hasUserSavedInDb when database contains user with given userId returns true`() {
        val userId = 1
        val user: User = mockk()
        coEvery { postsDao.getUser(userId) } returns user
        val result = runBlocking { subject.hasUserSavedInDb(userId) }

        coVerify { postsDao.getUser(userId) }
        assertThat(result).isTrue()
    }

    @Test
    fun `hasUserSavedInDb when database does not contains user with given userId returns false`() {
        val userId = 1
        coEvery { postsDao.getUser(userId) } returns null
        val result = runBlocking { subject.hasUserSavedInDb(userId) }

        coVerify { postsDao.getUser(userId) }
        assertThat(result).isFalse()
    }
}