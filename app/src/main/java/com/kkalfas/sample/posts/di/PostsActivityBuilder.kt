package com.kkalfas.sample.posts.di

import com.kkalfas.sample.commonui.viewmodel.ViewModelFactoryModule
import com.kkalfas.sample.posts.presentation.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class PostsActivityBuilder {
    @ContributesAndroidInjector(
        modules = [
            ViewModelFactoryModule::class,
            PostsActivityModule::class
        ]
    )
    internal abstract fun postsActivity(): PostsActivity
}