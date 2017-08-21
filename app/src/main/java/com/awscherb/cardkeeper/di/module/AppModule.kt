package com.awscherb.cardkeeper.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.awscherb.cardkeeper.data.CardKeeperDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides @Singleton fun provideContext() = context

    @Provides @Singleton
    fun provideDatabase(context: Context): CardKeeperDatabase {
        return Room.databaseBuilder(
                context.applicationContext, CardKeeperDatabase::class.java, "cardkeeper.db")
                .build()
    }
}