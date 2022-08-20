package com.svapp.rsplash.common.di

import com.svapp.rsplash.data.repository.DefaultPhotosRepository
import com.svapp.rsplash.data.repository.PhotosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun providePhotosRepository(
        repository: DefaultPhotosRepository
    ): PhotosRepository
}
