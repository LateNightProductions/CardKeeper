package com.awscherb.cardkeeper.di.module

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.BuildConfig
import com.awscherb.cardkeeper.core.BuildInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    fun provideBuildInfo() = BuildInfo(debug = BuildConfig.DEBUG)

}