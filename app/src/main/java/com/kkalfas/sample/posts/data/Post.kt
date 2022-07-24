package com.kkalfas.sample.posts.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Post(
    @Json @PrimaryKey val id: Int,
    @Json val userId: Int,
    @Json val title: String,
    @Json val body: String
)
