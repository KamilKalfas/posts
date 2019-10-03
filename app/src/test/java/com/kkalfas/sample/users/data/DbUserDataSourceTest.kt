package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.database.PostsDao
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DbUserDataSourceTest : MockkTest() {

    private val postsDao: PostsDao = mockk()
    private val subject = UserDataSource.Db(postsDao)

    @Test
    fun `getUser when user is not null returns right`() {
        val userId = 1
        coEvery { postsDao.getUser(userId) } returns mockk()

        val result = runBlocking { subject.getUser(userId) }

        coVerify { postsDao.getUser(userId) }
        assertThat(result).isInstanceOf(Either.Right::class.java)
    }

    @Test
    fun `getUser when user is null returns left`() {
        val userId = 1
        coEvery { postsDao.getUser(userId) } returns null

        val result = runBlocking { subject.getUser(userId) }

        coVerify { postsDao.getUser(userId) }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }
}