package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PostsRepositoryTest : MockkTest() {

    private val factory: PostsDataSource.Factory = mockk()
    private val dataSource: PostsDataSource = mockk()
    private val subject = PostsRepository.Impl(factory)

    @Test
    fun `getAllPosts calls dataSource getPosts`() {
        coEvery{ factory.create() } returns dataSource

        runBlocking { subject.getAllPosts() }
        coVerify {
            dataSource.getPosts()
        }
        coVerify { factory.create() }
    }
}