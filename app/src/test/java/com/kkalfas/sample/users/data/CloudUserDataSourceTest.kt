package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao
import io.mockk.Call
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CloudUserDataSourceTest : MockkTest() {

    private val networkService: NetworkService = mockk()
    private val postsDao: PostsDao = mockk()
    private val cacheManager : CacheManager = mockk()
    private val subject = UserDataSource.Cloud(networkService, postsDao, cacheManager)

    @Test
    fun `getUser when service gives error then return left with error`() {
        val userId = 1
        coEvery { networkService.getUser(userId) } returns Either.Left(Failure.ServerError)

        val result = runBlocking { subject.getUser(userId) }
        coVerify {
            networkService.getUser(userId)
            cacheManager.hasUserSavedInDb(any()) wasNot Called
            postsDao.insertUser(any()) wasNot Called
        }
        assertThat(result.isLeft).isTrue()
    }

    @Test
    fun `getUser when service gives response but user is saved in DB then returns user without inserting again`() {
        val userId = 1
        coEvery { networkService.getUser(userId) } returns Either.Right(User(userId, "name", "email"))
        coEvery { cacheManager.hasUserSavedInDb(userId)  } returns true

        val result = runBlocking { subject.getUser(userId) }

        coVerify {
            networkService.getUser(userId)
            cacheManager.hasUserSavedInDb(userId)
            postsDao.insertUser(any()) wasNot Called
        }
        assertThat(result.isRight).isTrue()
    }

    @Test
    fun `getUser when service gives response and user is not saved in DB then returns user with insertion`() {
        val user = User(1, "name", "email")
        coEvery { networkService.getUser(user.id) } returns Either.Right(user)
        coEvery { cacheManager.hasUserSavedInDb(user.id)  } returns false

        val result = runBlocking { subject.getUser(user.id) }
        coVerify {
            networkService.getUser(user.id)
            cacheManager.hasUserSavedInDb(user.id)
            postsDao.insertUser(user)
        }
        assertThat(result.isRight).isTrue()
    }
}