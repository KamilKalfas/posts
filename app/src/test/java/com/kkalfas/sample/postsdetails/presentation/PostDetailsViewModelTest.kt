package com.kkalfas.sample.postsdetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.commonui.PhotoUrlProvider
import com.kkalfas.sample.core.AppDispatcherProvider
import com.kkalfas.sample.core.Either
import com.kkalfas.sample.postsdetails.data.Comment
import com.kkalfas.sample.postsdetails.data.GetPostsDetails
import com.kkalfas.sample.postsdetails.data.PostDetails
import com.kkalfas.sample.postsdetails.presentation.PostDetailsViewModel.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailsViewModelTest : MockkTest() {

    private val observer: Observer<ViewState> = mockk()
    private val adapter: CommentsAdapter = mockk()
    private val appDispatcherProvider: AppDispatcherProvider = mockk()
    private val getPostsDetails: GetPostsDetails = mockk()
    private val subject: PostDetailsViewModel by lazy {
        PostDetailsViewModel(
            adapter,
            appDispatcherProvider,
            getPostsDetails
        )
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        every { appDispatcherProvider.io } returns Dispatchers.Unconfined
        subject.state.observeForever(observer)
    }

    @After
    fun after() {
        subject.state.removeObserver(observer)
    }

    @Test
    fun `load when postId and userId are equals to -1 then task is not invoked`() {
        val postId = -1
        val userId = -1

        subject.load(postId, userId)

        coVerify {
            appDispatcherProvider wasNot Called
            getPostsDetails wasNot Called
            adapter wasNot Called
        }
    }

    @Test
    fun `load when getPostsDetails returns success `() {
        val postId = 1
        val userId = 1
        val params = GetPostsDetails.Params(postId, userId)
        val comments: List<Comment> = listOf(mockk(), mockk())
        coEvery { getPostsDetails(params) } returns Either.Right(
            PostDetails(
                postId,
                title = "tile",
                username = "name",
                email = "mail",
                body = "body",
                comments = comments
            )
        )
        every { appDispatcherProvider.main } returns Dispatchers.Unconfined
        every { appDispatcherProvider.io } returns Dispatchers.Unconfined

        subject.load(postId, userId)

        coVerify {
            appDispatcherProvider.main
            appDispatcherProvider.io
            getPostsDetails(params)
            adapter.items = comments
        }
    }
}