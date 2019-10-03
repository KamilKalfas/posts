package com.kkalfas.sample.posts.di

import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.posts.data.*
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [PostsActivityBinds::class])
object PostsActivityModule {

    @JvmStatic
    @Provides
    fun provideGetPosts(postsRepository: PostsRepository): UseCase<Unit, List<Post>> {
        return GetPosts(postsRepository)
    }

    @JvmStatic
    @Provides
    fun provideGetUserPosts(
        getPosts: @JvmSuppressWildcards UseCase<Unit, List<Post>>,
        getUser: @JvmSuppressWildcards UseCase<GetUser.Params, User>
    ): UseCase<Unit, List<UserPost>> {
        return GetUserPosts(getUser, getPosts)
    }

    @JvmStatic
    @Provides
    fun providePostsRepository(factory: PostsDataSource.Factory): PostsRepository {
        return PostsRepository.Impl(factory)
    }

    @JvmStatic
    @Provides
    fun providePostsDataSourceFactory(
        @Named("cloud") cloudDataSource: PostsDataSource,
        @Named("db") databaseDataSource: PostsDataSource,
        cacheManager: CacheManager
    ): PostsDataSource.Factory {
        return PostsDataSource.Factory.Impl(cloudDataSource, databaseDataSource, cacheManager)
    }

    @JvmStatic
    @Provides
    @Named("cloud")
    fun provideCloudDataSource(
        networkService: NetworkService,
        postsDao: PostsDao
    ): PostsDataSource {
        return PostsDataSource.Cloud(networkService, postsDao)
    }

    @JvmStatic
    @Provides
    @Named("db")
    fun provideDatabaseDataSource(
        postsDao: PostsDao
    ): PostsDataSource {
        return PostsDataSource.Db(postsDao)
    }
}