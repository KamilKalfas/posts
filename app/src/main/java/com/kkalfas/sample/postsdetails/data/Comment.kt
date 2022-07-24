package com.kkalfas.sample.postsdetails.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Comment(
    @Json @PrimaryKey val id: Int,
    @Json val postId: Int,
    @Json val name: String,
    @Json val email: String,
    @Json val body: String
)
