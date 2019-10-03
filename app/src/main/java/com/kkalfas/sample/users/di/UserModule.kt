package com.kkalfas.sample.users.di

import com.kkalfas.sample.core.CacheManager
import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import com.kkalfas.sample.users.data.UserDataSource
import com.kkalfas.sample.users.data.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object UserModule {

    @JvmStatic
    @Provides
    fun providesGetUser(userRepository: UserRepository): UseCase<GetUser.Params, User> {
        return GetUser(userRepository)
    }

    @JvmStatic
    @Provides
    fun providesUserRepository(factory: UserDataSource.Factory): UserRepository {
        return UserRepository.Impl(factory)
    }

    @JvmStatic
    @Provides
    fun providesUserDataSourceFactory(
        @Named("cloud") cloudDataSource: UserDataSource,
        @Named("db") dbDataSource: UserDataSource,
        cacheManager: CacheManager
        ): UserDataSource.Factory {
        return UserDataSource.Factory.Impl(cloudDataSource, dbDataSource, cacheManager)
    }

    @JvmStatic
    @Provides
    @Named("cloud")
    fun providesCloudUserDataSource(
        networkService: NetworkService,
        postsDao: PostsDao,
        cacheManager: CacheManager
    ): UserDataSource {
        return UserDataSource.Cloud(networkService, postsDao, cacheManager)
    }

    @JvmStatic
    @Provides
    @Named("db")
    fun providesDbUserDataSource(
        postsDao: PostsDao
    ): UserDataSource {
        return UserDataSource.Db(postsDao)
    }
}