package com.kkalfas.sample.posts.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkalfas.sample.commonui.SnackbarActionCallback
import com.kkalfas.sample.core.AppDispatcherProvider
import com.kkalfas.sample.core.Failure
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.data.UserPost
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    adapter: UserPostsAdapter,
    private val dispatcherProvider: AppDispatcherProvider,
    private val getUserPosts: @JvmSuppressWildcards UseCase<Unit, List<UserPost>>
) : ViewModel(), SnackbarActionCallback {

    data class ViewState(
        val adapter: UserPostsAdapter,
        val loadingVisibility: Int,
        val postsVisibility: Int,
        val errorMessage: Int = -1,
        val isRefreshing: Boolean = false
    )

    private val _viewState = MutableLiveData<ViewState>().apply {
        value = ViewState(adapter, View.VISIBLE, View.GONE)
    }
    val state: LiveData<ViewState>
        get() = _viewState

    fun loadPost() {
        viewModelScope.launch(dispatcherProvider.io) {
            getUserPosts(Unit).either(
                onSuccess = {
                    state.value?.adapter?.items = it
                    _viewState.postValue(
                        state.value?.copy(
                            loadingVisibility = View.GONE,
                            postsVisibility = View.VISIBLE,
                            isRefreshing = false,
                            errorMessage = -1
                        )
                    )
                },
                onFailure = { onFailure(it) }
            )
        }
    }

    override fun onClick(p0: View?) {
        _viewState.value = state.value?.copy(
            isRefreshing = true,
            errorMessage = -1)
        loadPost()
    }

    private fun onFailure(failure: Failure) {
        val errorMessage = when (failure) {
            Failure.ServerError -> R.string.error_message_server
        }
        _viewState.postValue(
            state.value?.copy(
                loadingVisibility = View.GONE,
                postsVisibility = View.GONE,
                errorMessage = errorMessage,
                isRefreshing = false
            )
        )
    }
}