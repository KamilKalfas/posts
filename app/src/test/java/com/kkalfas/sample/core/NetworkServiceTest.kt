package com.kkalfas.sample.core

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.lang.Exception

class NetworkServiceTest : MockkTest() {

    private val api: Api = mockk()
    private val subject = NetworkService.Impl(api)

    @Test
    fun `getPosts when call is successful returns list of posts`() {
        val posts = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2")
        )
        coEvery { api.getPostsAsync() } returns posts

        val result = runBlocking { subject.getPosts() }
        val resultAsRight = result as Either.Right<List<Post>>
        coVerify { api.getPostsAsync() }

        assertThat(result).isInstanceOf(Either.Right::class.java)
        assertThat(resultAsRight.b).hasSize(2)
    }

    @Test
    fun `getPosts when call fails returns ServerError`() {
        coEvery { api.getPostsAsync() } throws Exception("no network")

        val result = runBlocking { subject.getPosts() }
        val resultAsLeft = result as Either.Left<Failure>
        coVerify { api.getPostsAsync() }

        assertThat(result).isInstanceOf(Either.Left::class.java)
        assertThat(resultAsLeft.a).isInstanceOf(Failure.ServerError::class.java)
    }

    @Test
    fun `getUser when call is successful returns list of users`() {
        val param = 1
        val user = User(1, "user1", "email1")
        coEvery { api.getUserAsync(param) } returns user

        val result = runBlocking { subject.getUser(param) }
        val resultAsRight = result as Either.Right<User>
        coVerify { api.getUserAsync(param) }

        assertThat(result).isInstanceOf(Either.Right::class.java)
        assertThat(resultAsRight.b).isInstanceOf(User::class.java)
    }

    @Test
    fun `getUser when call fails returns ServerError`() {
        val param = 1
        coEvery { api.getUserAsync(param) } throws Exception("no network")

        val result = runBlocking { subject.getUser(param) }
        val resultAsLeft = result as Either.Left<Failure>
        coVerify { api.getUserAsync(param) }

        assertThat(result).isInstanceOf(Either.Left::class.java)
        assertThat(resultAsLeft.a).isInstanceOf(Failure.ServerError::class.java)
    }
}