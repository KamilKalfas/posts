package com.kkalfas.sample.posts.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetUserPostsTest : MockkTest() {

    private val getUser: UseCase<GetUser.Params, User> = mockk()
    private val getPosts: UseCase<Unit, List<Post>> = mockk()
    private val subject = GetUserPosts(getUser, getPosts)

    @Test
    fun `invoke calls getPosts and getUser and maps result to UserPost`() {
        val userParamsSlot = mutableListOf<GetUser.Params>()
        val posts = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2")
        )
        val users = listOf(
            Either.Right(User(1, "user1", "email1")),
            Either.Right(User(2, "user2", "email2"))
        )
        coEvery { getPosts(Unit) } returns Either.Right(posts)
        coEvery { getUser(capture(userParamsSlot)) } returnsMany (users)

        val result = runBlocking { subject(Unit) }
        val listOfUserPosts = (result as Either.Right).b

        coVerify(exactly = 1) { getPosts(Unit) }
        coVerify(exactly = 2) { getUser(capture(userParamsSlot)) }

        assertThat(result.isRight).isTrue()
        assertThat(listOfUserPosts).hasSize(2)
        listOfUserPosts.forEachIndexed { index, userPost ->
            assertThat(userPost.userId).isEqualTo(users[index].b.id)
            assertThat(userPost.userName).isEqualTo(users[index].b.name)
            assertThat(userPost.postTitle).isEqualTo(posts[index].title)
            assertThat(userPost.postBody).isEqualTo(posts[index].body)
        }
    }

    @Test
    fun `invoke calls getPosts returns ServerError getUser is not called`() {
        coEvery { getPosts(Unit) } returns Either.Left(Failure.ServerError)

        val result = runBlocking { subject(Unit) }

        coVerify { getPosts(Unit) }
        coVerify { getUser(any()) wasNot Called }

        assertThat(result).isInstanceOf(Either.Left::class.java)
        assertThat((result as Either.Left).a).isInstanceOf(Failure.ServerError::class.java)
    }

    @Test
    fun `invoke when getUser returns Left then returns Empty user and filters UserPost entry`() {
        val userParamsSlot = mutableListOf<GetUser.Params>()
        val posts = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2"),
            Post(2, 3, "title3", "body3")
        )
        val usersResult = listOf(
            Either.Right(User(1, "user1", "email1")),
            Either.Left(Failure.ServerError),
            Either.Left(Failure.ServerError)
        )
        coEvery { getPosts(Unit) } returns Either.Right(posts)
        coEvery { getUser(capture(userParamsSlot)) } returnsMany (usersResult)


        val result = runBlocking { subject(Unit) }
        val listOfUserPosts = (result as Either.Right).b

        coVerify(exactly = 1) { getPosts(Unit) }
        coVerify(exactly = 3) { getUser(capture(userParamsSlot)) }

        assertThat(listOfUserPosts).hasSize(1)
        val user1 = (usersResult[0] as Either.Right).b
        assertThat(listOfUserPosts[0].userId).isEqualTo(user1.id)
        assertThat(listOfUserPosts[0].userName).isEqualTo(user1.name)
        assertThat(listOfUserPosts[0].postTitle).isEqualTo(posts[0].title)
        assertThat(listOfUserPosts[0].postBody).isEqualTo(posts[0].body)
    }
}