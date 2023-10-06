package com.awscherb.cardkeeper.di.module

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.BuildConfig
import com.awscherb.cardkeeper.core.BuildInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBuildInfo() = BuildInfo(debug = BuildConfig.DEBUG)

}