package com.kkalfas.sample.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PostsApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }
}