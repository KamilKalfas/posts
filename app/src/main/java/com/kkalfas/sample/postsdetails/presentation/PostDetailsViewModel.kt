package com.kkalfas.sample.postsdetails.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkalfas.sample.commonui.PhotoUrlProvider
import com.kkalfas.sample.core.AppDispatcherProvider
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.postsdetails.data.GetPostsDetails
import com.kkalfas.sample.postsdetails.data.PostDetails
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    adapter: CommentsAdapter,
    private val dispatcherProvider: AppDispatcherProvider,
    private val getPostsDetails: @JvmSuppressWildcards UseCase<GetPostsDetails.Params, PostDetails>
) : ViewModel() {

    data class ViewState(
        val adapter: CommentsAdapter,
        val title: String = "",
        val body: String = "",
        val username: String = "",
        val email: String = "",
        val backgroundUrl: String = "",
        val userId: Int = -1
    )

    private val _viewState = MutableLiveData<ViewState>().apply {
        value = ViewState(adapter = adapter)
    }

    val state: LiveData<ViewState>
        get() = _viewState

    fun load(postId: Int, userId: Int) {
        if (postId != -1 && userId != -1) {
            _viewState.value = state.value?.copy(
                backgroundUrl = PhotoUrlProvider.getDetailsPhotoUrl(postId),
                userId = userId
            )
            viewModelScope.launch(dispatcherProvider.io) {
                getPostsDetails(GetPostsDetails.Params(postId = postId, userId = userId)).either(
                    onSuccess = {
                        viewModelScope.launch(dispatcherProvider.main) {
                            state.value?.adapter?.items = it.comments
                            _viewState.postValue(
                                state.value?.copy(
                                    email = it.email,
                                    body = it.body,
                                    title = it.title,
                                    username = it.username
                                )
                            )
                        }
                    },
                    onFailure = {
                        Log.d("PostDetailsViewMode:onFailure", it.toString())
                    }
                )
            }
        }
    }
}