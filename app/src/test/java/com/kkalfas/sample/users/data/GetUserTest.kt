package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import io.mockk.coVerify
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetUserTest : MockkTest() {

    private val repository: UserRepository = mockk()
    private val subject = GetUser(repository)

    @Test
    fun `invoke call repository getUserById with extracted Param`() {
        val params = GetUser.Params(userId = 1)
        val paramSlot = slot<Int>()
        runBlocking { subject(params) }

        coVerify {
            repository.getUserByI(capture(paramSlot))
        }
        assertThat(paramSlot.captured).isEqualTo(params.userId)
    }
}