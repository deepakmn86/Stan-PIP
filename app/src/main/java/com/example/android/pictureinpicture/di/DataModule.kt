package com.example.android.pictureinpicture.di

import com.example.android.pictureinpicture.DefaultStopwatchRepository
import com.example.android.pictureinpicture.StopwatchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsDefaultStopwatchRepository(
        stopwatchRepository: DefaultStopwatchRepository
    ): StopwatchRepository
}