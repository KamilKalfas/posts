package com.kkalfas.sample.posts.data

data class UserPost(
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val postTitle: String,
    val postBody: String
) {
    companion object {
        val Empty = UserPost(-1, "", "", "", "")
    }

    fun isNotEmpty() = this != Empty
}