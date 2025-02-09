package com.code.newsapp.di

import android.app.Application
import androidx.room.Room
import com.code.newsapp.data.LocalUserManagerImpl
import com.code.newsapp.data.local.NewsDao
import com.code.newsapp.data.local.NewsDatabase
import com.code.newsapp.data.local.NewsTypeConverter
import com.code.newsapp.data.remote.dto.NewsApi
import com.code.newsapp.data.repository.NewsRepositoryImpl
import com.code.newsapp.domain.manager.LocalUserManager
import com.code.newsapp.domain.repository.NewsRepository
import com.code.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.code.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.code.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.code.newsapp.domain.usecases.news.DeleteArticle
import com.code.newsapp.domain.usecases.news.GetNews
import com.code.newsapp.domain.usecases.news.NewsUseCases
import com.code.newsapp.domain.usecases.news.SearchNews
import com.code.newsapp.domain.usecases.news.SelectArticle
import com.code.newsapp.domain.usecases.news.SelectArticles
import com.code.newsapp.domain.usecases.news.UpsertArticle
import com.code.newsapp.utils.Constants
import com.code.newsapp.utils.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager), saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi,newsDao: NewsDao): NewsRepository = NewsRepositoryImpl(newsApi,newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository, newsDao: NewsDao): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            context = application, klass = NewsDatabase::class.java, name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter()).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao




}