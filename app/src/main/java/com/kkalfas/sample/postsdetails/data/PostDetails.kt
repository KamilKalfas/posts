package com.kkalfas.sample.postsdetails.data

data class PostDetails(
    val postId: Int,
    val title: String,
    val body: String,
    val username: String,
    val email: String,
    val comments: List<Comment>
)
