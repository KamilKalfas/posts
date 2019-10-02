package com.kkalfas.sample.postsdetails.data

data class PostDetails(
    var title: String,
    var body: String,
    val username: String,
    val commentsCount : Int
)
