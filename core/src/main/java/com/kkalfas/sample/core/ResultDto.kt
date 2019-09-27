package com.kkalfas.sample.core

sealed class ResultDto {
    object Loading : ResultDto()
    data class Success<out T : Any>(val data: T) : ResultDto()
    data class Error(val throwable: Throwable) : ResultDto()
}

inline fun <reified T: Any> getResult(block: () -> T) : ResultDto {
    return try {
        ResultDto.Success(block.invoke())
    } catch (exception : Exception) {
        ResultDto.Error(exception)
    }
}