package com.kkalfas.sample.posts.data

data class UserPost(
    val userId: Int,
    val postId: Int,
    val userName: String,
    val postTitle: String,
    val postBody: String
) {
    companion object {
        val Empty = UserPost(-1, -1, "", "", "")
    }

    fun isNotEmpty() = this != Empty
}