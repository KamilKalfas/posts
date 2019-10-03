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
    abstract suspend fun getUser(userId: Int) : User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM Post WHERE Post.id = :postId")
    abstract suspend fun getPost(postId: Int) : Post

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertComments(comments: List<Comment>)

    @Query("SELECT COUNT(*) FROM Comment")
    abstract suspend fun commentsCount(): Int
}