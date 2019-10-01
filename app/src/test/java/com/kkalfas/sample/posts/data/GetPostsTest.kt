package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPostsTest : MockkTest() {

    private val repository: PostsRepository = mockk()
    private val subject = GetPosts(repository)

    @Test
    fun `invoke calls repository getAllPosts`() {
        runBlocking { subject(Unit) }
        coVerify { repository.getAllPosts() }
    }
}