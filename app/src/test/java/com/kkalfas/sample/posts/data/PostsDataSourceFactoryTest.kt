package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PostsDataSourceFactoryTest : MockkTest() {

    private val cloudDataSource: PostsDataSource.Cloud = mockk()
    private val subject = PostsDataSource.Factory.Impl(cloudDataSource)

    @Test
    fun `create returns cloudDataSource`() {
        assertThat(subject.create()).isInstanceOf(PostsDataSource.Cloud::class.java)
    }
}