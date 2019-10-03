package com.kkalfas.sample.users.di

import com.kkalfas.sample.core.NetworkService
import com.kkalfas.sample.core.UseCase
import com.kkalfas.sample.database.PostsDao
import com.kkalfas.sample.users.data.GetUser
import com.kkalfas.sample.users.data.User
import com.kkalfas.sample.users.data.UserDataSource
import com.kkalfas.sample.users.data.UserRepository
import dagger.Module
import dagger.Provides

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
    fun providesUserDataSourceFactory(userDataSource: UserDataSource): UserDataSource.Factory {
        return UserDataSource.Factory.Impl(userDataSource)
    }

    @JvmStatic
    @Provides
    fun providesCloudUserDataSource(
        networkService: NetworkService,
        postsDao: PostsDao
    ): UserDataSource {
        return UserDataSource.Cloud(networkService, postsDao)
    }
}