package com.example.challenge.di

import com.example.challenge.Retrofit.RetrofitProvider
import com.example.challenge.network.services.RickAndMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideService(): RickAndMortyService {
        return RetrofitProvider.retrofit.create(RickAndMortyService::class.java)
    }
}