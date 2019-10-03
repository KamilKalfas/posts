package com.kkalfas.sample.posts.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserPostTest {

    @Test
    fun `when subject is empty UserPost then isNotEmpty returns false`() {
        val subject = UserPost(-1, -1, "", "", "")
        assertThat(subject.isNotEmpty()).isFalse()
    }

    @Test
    fun `when subject is empty UserPost then isNotEmpty returns true`() {
        val subject = UserPost(1, -1, "userName", "title", "body")
        assertThat(subject.isNotEmpty()).isTrue()
    }
}