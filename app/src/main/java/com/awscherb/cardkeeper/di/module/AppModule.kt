package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.BuildConfig
import com.awscherb.cardkeeper.common.BuildInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBuildInfo() = BuildInfo(debug = BuildConfig.DEBUG)

}