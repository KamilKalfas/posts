package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.map
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User

interface PostDetailsMapper {

    suspend fun map(
        comments: Either<Failure, List<Comment>>,
        post: Post,
        user: User
    ): Either<Failure, PostDetails>

    class Impl : PostDetailsMapper {
        override suspend fun map(
            comments: Either<Failure, List<Comment>>,
            post: Post,
            user: User
        ): Either<Failure, PostDetails> {
            return when (comments) {
                is Either.Left -> Either.Left(comments.a)
                is Either.Right -> comments.map {
                    PostDetails(
                        postId = post.id,
                        title = post.title,
                        body = post.body,
                        username = user.name,
                        email = user.email,
                        comments = it
                    )
                }
            }
        }
    }
}
