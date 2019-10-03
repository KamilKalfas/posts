package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User

interface PostDetailsMapper {

    suspend fun map(
        comments: Either<Failure, List<Comment>>,
        post: Post,
        user: User
    ): Either<Failure, PostDetails>

    fun map(
        comments: List<Comment>,
        post: Post,
        user: User
    ): Either<Failure, PostDetails>

    class Impl : PostDetailsMapper {

        override fun map(
            comments: List<Comment>,
            post: Post,
            user: User
        ): Either<Failure, PostDetails> {
            return Either.Right(
                PostDetails(
                    postId = post.id,
                    title = post.title,
                    body = post.body,
                    username = user.name,
                    email = user.email,
                    comments = comments
                )
            )
        }

        override suspend fun map(
            comments: Either<Failure, List<Comment>>,
            post: Post,
            user: User
        ): Either<Failure, PostDetails> {
            return when (comments) {
                is Either.Left -> Either.Left(comments.a)
                is Either.Right -> map(comments.b, post, user)
            }
        }
    }
}
