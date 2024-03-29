package com.kkalfas.sample.posts.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kkalfas.sample.commonui.BaseActivity
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.databinding.ActivityPostsBinding
import javax.inject.Inject

class PostsActivity : BaseActivity() {
    override val layoutId = R.layout.activity_posts

    private lateinit var binding: ActivityPostsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PostsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bind()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.activityPostsView.lifecycleOwner = this
        viewModel.loadPost()
    }
}
