package com.kkalfas.sample.posts.di

import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.posts.data.*
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import dagger.Module
import dagger.Provides

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
    fun providePostsDataSourceFactory(dataSource: PostsDataSource): PostsDataSource.Factory {
        return PostsDataSource.Factory.Impl(dataSource)
    }

    @JvmStatic
    @Provides
    fun provideCloudeDataSource(
        networkService: NetworkService,
        postsDao: PostsDao
    ): PostsDataSource {
        return PostsDataSource.Cloud(networkService, postsDao)
    }
}