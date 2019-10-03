package com.kkalfas.sample.postsdetails.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.postsdetails.data.GetPostsDetails
import com.kkalfas.sample.postsdetails.data.PostDetails
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val getPostsDetails: @JvmSuppressWildcards UseCase<GetPostsDetails.Params, PostDetails>
) : ViewModel() {

    fun load(postId: Int, userId: Int) {
        viewModelScope.launch {
            getPostsDetails(GetPostsDetails.Params(postId, userId)).either(
                onSuccess = { Log.d("PostDetailsViewModel:onSuccess", it.toString()) },
                onFailure = { Log.d("PostDetailsViewMode:onFailure", it.toString()) }
            )
        }
    }
}