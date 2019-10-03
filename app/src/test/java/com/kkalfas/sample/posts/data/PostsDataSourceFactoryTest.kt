package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.CacheManager
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PostsDataSourceFactoryTest : MockkTest() {

    private val cloudDataSource: PostsDataSource.Cloud = mockk()
    private val dbDataSource: PostsDataSource.Db = mockk()
    private val cacheManager: CacheManager = mockk()
    private val subject = PostsDataSource.Factory.Impl(cloudDataSource, dbDataSource, cacheManager)

    @Test
    fun `create when cacheManager has PostsSavedInDb then return db source`() {
        coEvery { cacheManager.hasPostsSavedInDb() } returns true

        val result = runBlocking { subject.create() }

        coVerify { cacheManager.hasPostsSavedInDb() }
        assertThat(result).isInstanceOf(PostsDataSource.Db::class.java)
    }

    @Test
    fun `create when cacheManager has no PostsSavedInDb then return cloud source`() {
        coEvery { cacheManager.hasPostsSavedInDb() } returns false

        val result = runBlocking { subject.create() }

        coVerify { cacheManager.hasPostsSavedInDb() }
        assertThat(result).isInstanceOf(PostsDataSource.Cloud::class.java)
    }
}