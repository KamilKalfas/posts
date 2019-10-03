package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Test

class CloudPostDetailsDataSourceTest : MockkTest() {

    private val networkService: NetworkService = mockk()
    private val postsDao: PostsDao = mockk()
    private val postDetailsMapper: PostDetailsMapper = mockk()
    private val subject = PostDetailsDataSource.Cloud(networkService, postsDao, postDetailsMapper)

    private val userId = 1
    private val postId = 11

    @Test
    fun `getPostDetails when getUser is null then return left`() {
        coEvery { postsDao.getUser(userId) } returns null

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
        }
        coVerify(exactly = 0) {
            postsDao.getPost(any())
            postsDao.getComments(any())
            postDetailsMapper
        }
        Assertions.assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getUser throws Exception then return left`() {
        coEvery { postsDao.getUser(userId) } throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
        }
        coVerify(exactly = 0) {
            postsDao.getPost(any())
            networkService.getComments(any())
            postsDao.getComments(any())
            postDetailsMapper
        }
        Assertions.assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getPost throws Exception then return left`() {
        coEvery { postsDao.getUser(userId) } returns mockk()
        coEvery { postsDao.getPost(postId) } throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
        }
        coVerify(exactly = 0) {
            networkService.getComments(any())
            postsDao.getComments(any())
            postDetailsMapper
        }
        Assertions.assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getComments throws Exception then call networkService getComments which fails then return left`() {
        coEvery { postsDao.getUser(userId) } returns mockk()
        coEvery { postsDao.getPost(postId) } returns mockk()
        coEvery { postsDao.getComments(postId) } throws Exception()
        coEvery { networkService.getComments(postId) } throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
            postsDao.getComments(postId)
            networkService.getComments(postId)
        }
        coVerify(exactly = 0) {
            postDetailsMapper wasNot Called
        }
        Assertions.assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when all calls succeed then calls postDetailsMapper and return right`() {
        val user = User(1, "user1", "email1")
        val post = Post(1, 1, "title1", "body1")
        val comments: List<Comment> = listOf(Comment(1, 1, "", "", ""))
        val commentsAsEither : Either<Failure, List<Comment>> = Either.Right(comments)
        val mapped : Either<Failure, PostDetails> = Either.Right(mockk())
        coEvery { postsDao.getUser(userId) } returns user
        coEvery { postsDao.getPost(postId) } returns post
        coEvery { postsDao.getComments(postId) } returns comments
        coEvery { postDetailsMapper.map(commentsAsEither, post, user) } returns mapped

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
            postsDao.getComments(postId)
            postDetailsMapper.map(commentsAsEither, post, user)
        }
        coVerify(exactly = 0) {
            networkService.getComments(any())
        }
        Assertions.assertThat(result).isInstanceOf(Either.Right::class.java)
    }
}