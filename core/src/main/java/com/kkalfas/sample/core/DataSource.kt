package com.kkalfas.sample.core

interface DataSource {
    interface Query

    interface Factory {
        fun create() : DataSource
    }

    suspend fun get(query: Query) : ResultDto
}
