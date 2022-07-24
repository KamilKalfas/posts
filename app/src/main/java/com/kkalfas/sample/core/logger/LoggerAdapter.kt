package com.kkalfas.sample.core.logger

import timber.log.Timber

interface LoggerAdapter {
    fun log(message: String) = Unit
    fun log(exception: Exception) = Unit
    fun log(message: String, throwable: Throwable) = Unit
}

object TimberAdapter : LoggerAdapter {
    private const val TAG = "APP_LOGGER"

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun log(message: String) = Timber.tag(TAG).d(message)
    override fun log(exception: Exception) = Timber.tag(TAG).d(exception)
    override fun log(message: String, throwable: Throwable) = Timber.tag(TAG).d(t = throwable, message = message)
}
