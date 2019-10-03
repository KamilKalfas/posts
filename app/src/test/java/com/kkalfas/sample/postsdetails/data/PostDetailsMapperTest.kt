package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import io.mockk.Called
import io.mockk.coVerify
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PostDetailsMapperTest : MockkTest() {
    private val subject = PostDetailsMapper.Impl()

    @Test
    fun `map transforms comments, user and post into PostDetails`() {
        val post: Post = mockk()
        val user: User = mockk()
        val comments: List<Comment> = listOf(mockk())

        val result = subject.map(comments, post, user)
        verify {
            post.id
            post.title
            post.body
            user.name
            user.email
        }

        assertThat(result).isInstanceOf(Either.Right::class.java)
        assertThat((result as Either.Right).b.comments).isEqualTo(comments)
    }

    @Test
    fun `map when eitherComments is right then transforms comments, user and post into PostDetails`() {
        val post: Post = mockk()
        val user: User = mockk()
        val comments: List<Comment> = listOf(mockk())
        val eitherComments: Either<Failure, List<Comment>> = Either.Right(comments)

        val result = runBlocking { subject.map(eitherComments, post, user) }
        verify {
            post.id
            post.title
            post.body
            user.name
            user.email
            (eitherComments as Either.Right).b
        }

        assertThat(result).isInstanceOf(Either.Right::class.java)
        assertThat((result as Either.Right).b.comments).isEqualTo(comments)
    }

    @Test
    fun `map when eitherComments is left then return left`() {
        val post: Post = mockk()
        val user: User = mockk()
        val eitherComments: Either<Failure, List<Comment>> = Either.Left(Failure.ServerError)

        val result = runBlocking { subject.map(eitherComments, post, user) }
        coVerify(exactly = 0) {
            post.id
            post.title
            post.body
            user.name
            user.email
        }

        assertThat(result).isInstanceOf(Either.Left::class.java)
    }
}