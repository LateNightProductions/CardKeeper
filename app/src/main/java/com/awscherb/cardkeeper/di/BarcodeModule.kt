package com.awscherb.cardkeeper.di

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.barcode.db.BarcodeDatabase
import com.awscherb.cardkeeper.barcode.db.migrations.MIGRATION_11_12_ADD_RESULT
import com.awscherb.cardkeeper.barcode.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BarcodeModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): BarcodeDatabase {
            return Room.databaseBuilder(
                // using name from original database, hopefully that works
                context.applicationContext, BarcodeDatabase::class.java, "cardkeeper.db"
            )
                .addMigrations(
                    MIGRATION_11_12_ADD_RESULT
                )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideScannedCodeDao(db: BarcodeDatabase) = db.scannedCodeDao()

    }

    @Binds
    fun bindScannedCodeService(handler: ScannedCodeHandler): ScannedCodeService
}