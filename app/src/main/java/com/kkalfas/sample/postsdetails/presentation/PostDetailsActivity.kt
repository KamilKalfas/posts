package com.kkalfas.sample.postsdetails.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kkalfas.sample.commonui.BaseActivity
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.databinding.ActivityPostDetailsBinding
import javax.inject.Inject

const val POST_ID_KEY = "PostDetailsActivity_postIdKey"
const val USER_ID_KEY = "PostDetailsActivity_userIdKey"

class PostDetailsActivity : BaseActivity() {
    override val layoutId = R.layout.activity_post_details

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PostDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPostDetailsBinding = bind()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val postId = intent.getIntExtra(POST_ID_KEY, -1)
        val userId = intent.getIntExtra(USER_ID_KEY, -1)
        viewModel.load(postId, userId)
    }

    fun toolbarBackPressed(view: View) {
        onBackPressed()
    }
}