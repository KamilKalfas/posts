package com.kkalfas.sample.users.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @Json @PrimaryKey val id: Int,
    @Json val name: String,
    @Json val email: String
)
