package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.database.PostsDao
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class DbPostsDataSourceTest : MockkTest() {

    private val postsDao: PostsDao = mockk()
    private val subject = PostsDataSource.Db(postsDao)

    @Test
    fun `getPosts when there is no posts in db then return left`() {
        coEvery { postsDao.getPosts() } throws Exception()

        val result = runBlocking { subject.getPosts() }

        coVerify { postsDao.getPosts() }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPosts when there are posts in db then return right`() {
        coEvery { postsDao.getPosts() } returns listOf(mockk())

        val result = runBlocking { subject.getPosts() }

        coVerify { postsDao.getPosts() }
        assertThat(result).isInstanceOf(Either.Right::class.java)
    }
}