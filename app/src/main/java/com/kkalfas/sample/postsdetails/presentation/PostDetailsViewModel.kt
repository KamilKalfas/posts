package com.kkalfas.sample.postsdetails.presentation

import androidx.lifecycle.ViewModel
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.postsdetails.data.GetPostsDetails
import com.kkalfas.sample.postsdetails.data.PostDetails
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val getPostsDetails: @JvmSuppressWildcards UseCase<GetPostsDetails.Params, PostDetails>
) : ViewModel()