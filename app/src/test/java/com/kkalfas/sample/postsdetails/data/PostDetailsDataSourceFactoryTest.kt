package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.CacheManager
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PostDetailsDataSourceFactoryTest : MockkTest() {

    private val cloudDataSource: PostDetailsDataSource.Cloud = mockk()
    private val dbDataSource: PostDetailsDataSource.Db = mockk()
    private val cacheManager: CacheManager = mockk()
    private val subject =
        PostDetailsDataSource.Factory.Impl(cloudDataSource, dbDataSource, cacheManager)

    @Test
    fun `create when cacheManager has CommentsSavedInDb returns dbDataSource`() {
        coEvery { cacheManager.hasCommentsSavedInDb() } returns true

        val dataSource = runBlocking { subject.create() }

        coVerify { cacheManager.hasCommentsSavedInDb() }
        assertThat(dataSource).isInstanceOf(PostDetailsDataSource.Db::class.java)
    }

    @Test
    fun `create when cacheManager has no CommentsSavedInDb returns cloudDataSource`() {
        coEvery { cacheManager.hasCommentsSavedInDb() } returns false

        val dataSource = runBlocking { subject.create() }

        coVerify { cacheManager.hasCommentsSavedInDb() }
        assertThat(dataSource).isInstanceOf(PostDetailsDataSource.Cloud::class.java)
    }
}