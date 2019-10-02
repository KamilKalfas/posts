package com.kkalfas.sample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.postsdetails.data.Comment
import com.kkalfas.sample.users.data.User

@Database(
    entities = [
        User::class,
        Post::class,
        Comment::class
    ], version = 1
)
abstract class PostsAppDatabase : RoomDatabase() {
    companion object {
        const val NAME = "postsapp_db"
    }

    abstract fun dao(): PostsDao
}