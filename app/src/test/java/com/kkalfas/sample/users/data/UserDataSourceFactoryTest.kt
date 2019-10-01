package com.kkalfas.sample.users.data

import com.kkalfas.sample.MockkTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserDataSourceFactoryTest : MockkTest() {

    private val cloudDataSource: UserDataSource.Cloud = mockk()
    private val subject = UserDataSource.Factory.Impl(cloudDataSource)

    @Test
    fun `create return cloudDataSource`() {
        assertThat(subject.create()).isInstanceOf(UserDataSource.Cloud::class.java)
    }
}