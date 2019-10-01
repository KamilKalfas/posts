package com.kkalfas.sample.posts.di

import androidx.lifecycle.ViewModel
import com.kkalfas.sample.posts.presentation.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
abstract class PostsActivityBinds {

    @Binds
    @IntoMap
    @StringKey("PostsViewModel")
    abstract fun bindPostsViewModel(viewModel: PostsViewModel): ViewModel
}