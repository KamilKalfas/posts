package com.kkalfas.sample.postsdetails.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kkalfas.sample.commonui.BaseActivity
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.databinding.ActivityPostDetailsBinding
import javax.inject.Inject

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
    }
}