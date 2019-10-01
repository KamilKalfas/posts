package com.kkalfas.sample.core

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatcherProvider(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)