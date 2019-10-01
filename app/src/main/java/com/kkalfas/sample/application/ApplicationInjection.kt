package com.kkalfas.sample.application

import android.app.Application
import android.content.Context
import com.kkalfas.sample.core.AppDispatcherProvider
import com.kkalfas.sample.core.ApplicationScope
import com.kkalfas.sample.core.NetworkModule
import com.kkalfas.sample.posts.di.PostsActivityBuilder
import com.kkalfas.sample.users.di.UserModule
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        PostsActivityBuilder::class,
        UserModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<PostsApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: PostsApplication): ApplicationComponent
    }
}

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @JvmStatic
    @Provides
    @ApplicationScope
    fun provideContext(application: PostsApplication): Context = application.applicationContext


    @JvmStatic
    @Provides
    @Singleton
    fun provideDispatcherProvider() = AppDispatcherProvider(
        main = Dispatchers.Main,
        io = Dispatchers.IO
    )
}

@Module
abstract class ApplicationModuleBinds {

    @Binds
    abstract fun provideApplication(bind: PostsApplication): Application
}