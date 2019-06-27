package com.awscherb.cardkeeper.di.module

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.data.CardKeeperDatabase
import com.awscherb.cardkeeper.data.DatabaseMigrations
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CardKeeperDatabase {
        return Room.databaseBuilder(
            context.applicationContext, CardKeeperDatabase::class.java, "cardkeeper.db"
        )
            .addMigrations(DatabaseMigrations.MIGRATE_10_TO_11)
            .build()
    }
}