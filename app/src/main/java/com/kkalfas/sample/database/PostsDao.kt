package com.kkalfas.sample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.postsdetails.data.Comment
import com.kkalfas.sample.users.data.User

@Dao
abstract class PostsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE User.id = :userId")
    abstract suspend fun getUser(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM Post WHERE Post.id = :postId")
    abstract suspend fun getPost(postId: Int): Post

    @Query("SELECT * FROM Post")
    abstract suspend fun getPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertComments(comments: List<Comment>)

    @Query("SELECT COUNT(*) FROM Comment")
    abstract suspend fun commentsCount(): Int

    @Query("SELECT COUNT(*) FROM Post")
    abstract suspend fun postsCount(): Int

    @Query("SELECT COUNT(*) FROM User")
    abstract suspend fun userCount(): Int

    @Query("SELECT * FROM Comment WHERE Comment.postId = :postId")
    abstract fun getComments(postId: Int): List<Comment>
}