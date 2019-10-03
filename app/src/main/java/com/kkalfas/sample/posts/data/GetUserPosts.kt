package com.kkalfas.sample.posts.data

import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import java.lang.Exception

class GetUserPosts(
    private val getUser: @JvmSuppressWildcards UseCase<GetUser.Params, User>,
    private val getPosts: @JvmSuppressWildcards UseCase<Unit, List<Post>>
) : UseCase<Unit, List<UserPost>> {

    override suspend fun invoke(params: Unit): Either<Failure, List<UserPost>> {
        return when (val posts = getPosts(params)) {
            is Either.Left -> Either.Left(posts.a)
            is Either.Right -> Either.Right(
                posts.b.map { toUserPost(it) }
                    .filter { it.isNotEmpty() })
        }
    }

    private suspend fun toUserPost(post: Post): UserPost {
        return when (val user = getUser(GetUser.Params(post.userId))) {
            is Either.Left -> UserPost.Empty
            is Either.Right -> UserPost(
                postId =  post.id,
                userId = user.b.id,
                userName = user.b.name,
                postTitle = post.title,
                postBody = post.body
            )
        }
    }
}