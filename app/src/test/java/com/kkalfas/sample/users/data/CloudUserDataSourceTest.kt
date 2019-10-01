package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.NetworkService
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CloudUserDataSourceTest : MockkTest() {

    private val networkService: NetworkService = mockk()
    private val subject = UserDataSource.Cloud(networkService)

    @Test
    fun `getUser calls service getUserId with id`() {
        val userId = 1

        runBlocking { subject.getUser(userId) }
        coVerify {
            networkService.getUser(userId)
        }
    }
}