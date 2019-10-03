package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserRepositoryTest : MockkTest() {

    private val factory : UserDataSource.Factory = mockk()
    private val dataSource : UserDataSource = mockk()
    private val subject = UserRepository.Impl(factory)

    @Test
    fun `getUserById calls dataSource and passes userId`() {
        val userId = 1
        coEvery { factory.create() } returns dataSource

        runBlocking { subject.getUserByI(userId) }

        coVerify { dataSource.getUser(userId) }
        coVerify { factory.create() }
    }
}