package com.dm.berxley.newsapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dm.berxley.newsapp.data.local.NewsDao
import com.dm.berxley.newsapp.data.local.NewsDatabase
import com.dm.berxley.newsapp.data.local.NewsTypeConverter
import com.dm.berxley.newsapp.data.manager.LocalUserManagerImpl
import com.dm.berxley.newsapp.data.remote.NewsApi
import com.dm.berxley.newsapp.data.repository.NewsRepositoryImpl
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import com.dm.berxley.newsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)


    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): NewsDatabase {

        return Room.databaseBuilder(
            context = application,
            name = Constants.ROOM_DB_NAME,
            klass = NewsDatabase::class.java
        )
            .addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideNewsDao( newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao

}