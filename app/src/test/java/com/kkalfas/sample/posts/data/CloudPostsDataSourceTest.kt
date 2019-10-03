package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CloudPostsDataSourceTest : MockkTest() {

    private val networkService: NetworkService = mockk()
    private val postsDao : PostsDao = mockk()
    private val subject = PostsDataSource.Cloud(networkService, postsDao)

    @Test
    fun `getPosts when service gives error then return left`() {
        coEvery { networkService.getPosts() } returns Either.Left(Failure.ServerError)

        val result = runBlocking { subject.getPosts() }

        coVerify {
            networkService.getPosts()
            postsDao.insertPosts(any()) wasNot Called
        }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPosts when service returns posts then insert posts into db and return right`() {
        val post = Post(1, 1, "title", "body")
        coEvery { networkService.getPosts() } returns Either.Right(listOf(post))

        val result = runBlocking { subject.getPosts() }

        coVerify {
            networkService.getPosts()
            postsDao.insertPosts(listOf(post))
        }
        assertThat(result).isInstanceOf(Either.Right::class.java)
    }
}