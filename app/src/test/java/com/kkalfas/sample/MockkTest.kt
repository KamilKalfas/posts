package com.kkalfas.sample

import androidx.annotation.CallSuper
import io.mockk.MockK
import io.mockk.MockKDsl
import io.mockk.clearMocks
import org.junit.After
import kotlin.reflect.KClass

abstract class MockkTest {

    val mockList = ArrayList<Any>()

    @After
    @CallSuper
    fun resetMocks() {
        mockList.forEach {
            clearMocks(it)
        }
    }

    inline fun <reified T : Any> mockk(
        name: String? = null,
        relaxed: Boolean = true,
        vararg moreInterfaces: KClass<*>,
        relaxUnitFun: Boolean = true,
        instance: Boolean = false,
        block: T.() -> Unit = {}
    ): T {
        val mock = MockK.useImpl {
            MockKDsl.internalMockk(
                name,
                relaxed,
                *moreInterfaces,
                relaxUnitFun = relaxUnitFun,
                block = block
            )
        }
        if (!instance) mockList.add(mock)
        return mock
    }
}