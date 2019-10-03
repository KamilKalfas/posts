package com.kkalfas.sample.postsdetails.di

import androidx.lifecycle.ViewModel
import com.kkalfas.sample.commonui.viewmodel.ViewModelFactoryModule
import com.kkalfas.sample.core.CacheManager
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
import javax.inject.Named

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
        @Named("cloud") cloudDataSource: PostDetailsDataSource,
        @Named("db") dbSource: PostDetailsDataSource,
        cacheManager: CacheManager
    ): PostDetailsDataSource.Factory {
        return PostDetailsDataSource.Factory.Impl(
            cloudDataSource, dbSource, cacheManager
        )
    }

    @JvmStatic
    @Provides
    fun providePostDetailsMapper(): PostDetailsMapper = PostDetailsMapper.Impl()

    @JvmStatic
    @Provides
    @Named("cloud")
    fun provideCloudPostDetailsDataSource(
        networkService: NetworkService,
        postsDao: PostsDao,
        postDetailsMapper: PostDetailsMapper
    ): PostDetailsDataSource {
        return PostDetailsDataSource.Cloud(networkService, postsDao, postDetailsMapper)
    }

    @JvmStatic
    @Provides
    @Named("db")
    fun provideDbPostDetailsDataSource(
        postsDao: PostsDao,
        postDetailsMapper: PostDetailsMapper
    ): PostDetailsDataSource {
        return PostDetailsDataSource.Db(postsDao, postDetailsMapper)
    }
}