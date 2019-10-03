package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetPostsDetailsTest : MockkTest() {

    private val repository: PostDetailsRepository = mockk()
    private val subject = GetPostsDetails(repository)

    @Test
    fun `invoke call repository with right params`() {
        val paramsSlot = mutableListOf<Int>()
        val params = GetPostsDetails.Params(1, 2)

        runBlocking { subject(params) }
        coVerify {
            repository.getPostDetails(
                capture(paramsSlot),
                capture(paramsSlot)
            )
        }
        assertThat(paramsSlot[0]).isEqualTo(params.postId)
        assertThat(paramsSlot[1]).isEqualTo(params.userId)
    }
}