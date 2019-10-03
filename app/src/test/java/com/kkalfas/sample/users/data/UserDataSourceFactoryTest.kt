package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.database.PostsDao
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserDataSourceFactoryTest : MockkTest() {

    private val cloudDataSource: UserDataSource.Cloud = mockk()
    private val dbDataSource: UserDataSource.Db = mockk()
    private val cacheManager : CacheManager = mockk()
    private val subject = UserDataSource.Factory.Impl(cloudDataSource, dbDataSource, cacheManager)

    @Test
    fun `create when cacheManager has UsersSavedInDb then return db source`() {
        val dataSource = runBlocking { subject.create() }

        coVerify { cacheManager.hasUsersSavedInDb() }
        assertThat(dataSource).isInstanceOf(UserDataSource.Db::class.java)
    }

    @Test
    fun `create when cacheManager has no UsersSavedInDb then return cloud source`() {
        val dataSource = runBlocking { subject.create() }

        coVerify { cacheManager.hasUsersSavedInDb() }
        assertThat(dataSource).isInstanceOf(UserDataSource.Cloud::class.java)
    }
}