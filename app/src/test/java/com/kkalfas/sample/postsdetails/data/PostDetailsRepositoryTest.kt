package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PostDetailsRepositoryTest : MockkTest() {

    private val factory: PostDetailsDataSource.Factory = mockk()
    private val dataSource: PostDetailsDataSource = mockk()
    private val subject = PostDetailsRepository.Impl(factory)

    @Test
    fun `getPostDetails calls factory create then getPostDetails with correct params`() {
        val postId = 11
        val userId = 10
        coEvery { factory.create() } returns dataSource

        runBlocking { subject.getPostDetails(postId, userId) }
        coVerify {
            factory.create()
            dataSource.getPostDetails(postId, userId)
        }
    }
}