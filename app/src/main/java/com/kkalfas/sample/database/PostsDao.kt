package com.kkalfas.sample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.postsdetails.data.Comment
import com.kkalfas.sample.users.data.User

@Dao
abstract class PostsDao {

    @Insert
    abstract suspend fun saveUsers(user: User)

    @Insert
    abstract suspend fun savePosts(posts: List<Post>)

    @Insert
    abstract suspend fun saveComments(comments: List<Comment>)

    @Query("SELECT COUNT(*) FROM Comment")
    abstract suspend fun commentsCount(): Int
}