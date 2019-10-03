package com.kkalfas.sample.commonui

object PhotoUrlProvider {

    fun getAvatarUrl(userId: Int) : String = "https://i.pravatar.cc/150?u=$userId"
    fun getDetailsPhotoUrl(postId: Int) : String = "https://picsum.photos/id/$postId/300/200"
}