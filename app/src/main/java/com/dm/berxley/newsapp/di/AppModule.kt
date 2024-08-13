package com.dm.berxley.newsapp.di

import android.app.Application
import com.dm.berxley.newsapp.data.manager.LocalUserManagerImpl
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager = LocalUserManagerImpl(application)
}