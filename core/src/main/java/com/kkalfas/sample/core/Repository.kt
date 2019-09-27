package com.kkalfas.sample.core

interface Repository {
    interface Query
    suspend fun query(query: Query) : ResultDto
}