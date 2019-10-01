package com.kkalfas.sample.commonui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class ViewModelFactoryModule {

    @Provides
    internal fun providesViewModelFactory(
        viewModels: MutableMap<String, Provider<ViewModel>>
    ): ViewModelProvider.Factory = ViewModelFactory(viewModels)
}