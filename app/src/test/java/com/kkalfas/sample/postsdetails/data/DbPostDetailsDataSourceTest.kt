package com.kkalfas.sample.postsdetails.data

import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.posts.data.Post
import com.kkalfas.sample.users.data.User
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DbPostDetailsDataSourceTest : MockkTest() {

    private val postsDao: PostsDao = mockk()
    private val postDetailsMapper: PostDetailsMapper = mockk()
    private val subject = PostDetailsDataSource.Db(postsDao, postDetailsMapper)

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
            postsDao.getPost(any()) wasNot Called
            postsDao.getComments(any()) wasNot Called
            postDetailsMapper wasNot Called
        }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getUser throws Exception then return left`() {
        coEvery { postsDao.getUser(userId) } throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
        }
        coVerify(exactly = 0) {
            postsDao.getPost(any()) wasNot Called
            postsDao.getComments(any()) wasNot Called
            postDetailsMapper wasNot Called
        }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getPost throws Exception then return left`() {
        coEvery { postsDao.getUser(userId) } returns mockk()
        coEvery { postsDao.getPost(postId)} throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
        }
        coVerify(exactly = 0) {
            postsDao.getComments(any()) wasNot Called
            postDetailsMapper wasNot Called
        }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when getComments throws Exception then return left`() {
        coEvery { postsDao.getUser(userId) } returns mockk()
        coEvery { postsDao.getPost(postId)} returns mockk()
        coEvery { postsDao.getComments(postId)} throws Exception()

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
            postsDao.getComments(postId)
        }
        coVerify(exactly = 0) {
            postDetailsMapper wasNot Called
        }
        assertThat(result).isInstanceOf(Either.Left::class.java)
    }

    @Test
    fun `getPostDetails when all calls succeed then calls postDetailsMapper and return right`() {
        val user : User = mockk()
        val post : Post = mockk()
        val comments : List<Comment> = listOf(mockk())
        coEvery { postsDao.getUser(userId) } returns user
        coEvery { postsDao.getPost(postId)} returns post
        coEvery { postsDao.getComments(postId)} returns comments
        coEvery { postDetailsMapper.map(comments, post, user) } returns Either.Right(mockk())

        val result = runBlocking { subject.getPostDetails(postId, userId) }

        coVerify(exactly = 1) {
            postsDao.getUser(userId)
            postsDao.getPost(postId)
            postsDao.getComments(postId)
            postDetailsMapper.map(comments, post, user)
        }
        assertThat(result).isInstanceOf(Either.Right::class.java)
    }
}