package com.kkalfas.sample.posts.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kkalfas.sample.MockkTest
import com.kkalfas.sample.commonui.DataBinding
import com.kkalfas.sample.commonui.LayoutInflaterProvider
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.data.UserPost
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserPostsAdapterTest : MockkTest() {

    private val layoutInflaterProvider: LayoutInflaterProvider = mockk()
    private val dataBinding: DataBinding = mockk()
    private val subject = PostsAdapter(layoutInflaterProvider, dataBinding)

    @Test
    fun `onCreateViewHolder creates view holder with item_user_post layout`() {
        val parent: ViewGroup = mockk()
        val parentContext: Context = mockk()
        val inflater: LayoutInflater = mockk()
        every { layoutInflaterProvider.get(any()) } returns inflater
        every { parent.context } returns parentContext

        subject.onCreateViewHolder(parent, 0)

        verify {
            parent.context
            layoutInflaterProvider.get(parentContext)
            dataBinding.inflate(inflater, R.layout.item_user_post, parent, false)
        }
    }

    @Test
    fun `areItemsTheSame returns true when items are not equal`() {
        val userPost1 = UserPost(
            userId = 1,
            userName = "user1",
            userEmail = "user@mail",
            postTitle = "title",
            postBody = "body"
        )
        val userPost2 = UserPost(
            userId = 2,
            userName = "user1",
            userEmail = "user@mail",
            postTitle = "title",
            postBody = "body"
        )
        assertThat(subject.areItemsTheSame(userPost1, userPost2)).isFalse()
    }

    @Test
    fun `areItemsTheSame returns false when items are same`() {
        val userPost1 = UserPost(
            userId = 1,
            userName = "user1",
            userEmail = "user@mail",
            postTitle = "title",
            postBody = "body"
        )
        assertThat(subject.areItemsTheSame(userPost1, userPost1)).isTrue()
    }
}