package com.kkalfas.sample.postsdetails.di

import androidx.lifecycle.ViewModel
import com.kkalfas.sample.commonui.viewmodel.ViewModelFactoryModule
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.postsdetails.data.*
import com.kkalfas.sample.postsdetails.presentation.PostDetailsActivity
import com.kkalfas.sample.postsdetails.presentation.PostDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
abstract class PostDetailsActivityBinds {

    @Binds
    @IntoMap
    @StringKey("PostDetailsViewModel")
    abstract fun bindPostDetailsViewModel(postDetailsViewModel: PostDetailsViewModel): ViewModel
}

@Module
internal abstract class PostDetailsActivityBuilder {
    @ContributesAndroidInjector(
        modules = [
            ViewModelFactoryModule::class,
            PostDetailsActivityModule::class
        ]
    )
    internal abstract fun postDetailsActivity(): PostDetailsActivity
}

@Module(includes = [PostDetailsActivityBinds::class])
object PostDetailsActivityModule {

    @JvmStatic
    @Provides
    fun provideGetPostsDetails(
        postDetailsRepository: PostDetailsRepository
    ): UseCase<GetPostsDetails.Params, PostDetails> {
        return GetPostsDetails(postDetailsRepository)
    }

    @JvmStatic
    @Provides
    fun providePostDetailsRepository(
        factory: PostDetailsDataSource.Factory
    ): PostDetailsRepository {
        return PostDetailsRepository.Impl(factory)
    }

    @JvmStatic
    @Provides
    fun providePostDetailsDataSourceFactory(
        dataSource: PostDetailsDataSource
    ): PostDetailsDataSource.Factory {
        return PostDetailsDataSource.Factory.Impl(dataSource)
    }

    @JvmStatic
    @Provides
    fun providePostDetailsMapper() : PostDetailsMapper = PostDetailsMapper.Impl()

    @JvmStatic
    @Provides
    fun provideCloudPostDetailsDataSource(
        networkService: NetworkService,
        postsDao: PostsDao,
        postDetailsMapper: PostDetailsMapper
    ): PostDetailsDataSource {
        return PostDetailsDataSource.Cloud(networkService, postsDao, postDetailsMapper)
    }
}