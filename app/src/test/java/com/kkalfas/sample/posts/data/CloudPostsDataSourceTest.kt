package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.NetworkService
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CloudPostsDataSourceTest : MockkTest() {

    private val networkService: NetworkService = mockk()
    private val subject = PostsDataSource.Cloud(networkService)

    @Test
    fun `getPosts call service getPosts`() {
        runBlocking { subject.getPosts() }
        coVerify { networkService.getPosts() }
    }
}