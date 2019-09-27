package com.kkalfas.sample.core

interface UseCase {
    interface Params
    suspend fun run(params: Params): ResultDto
}