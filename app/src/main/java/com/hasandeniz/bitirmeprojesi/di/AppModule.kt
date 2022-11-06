package com.hasandeniz.bitirmeprojesi.di

import com.hasandeniz.bitirmeprojesi.data.datasource.FoodDataSource
import com.hasandeniz.bitirmeprojesi.data.repo.FoodRepository
import com.hasandeniz.bitirmeprojesi.retrofit.ApiUtils
import com.hasandeniz.bitirmeprojesi.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodRepository(fds:FoodDataSource) : FoodRepository{
        return FoodRepository(fds)
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(fdao:FoodDao) : FoodDataSource{
        return FoodDataSource(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodDao{
        return ApiUtils.getFoodDao()
    }

    @Provides
    fun provideHiltApplication() : HiltApplication{
        return HiltApplication()
    }

}