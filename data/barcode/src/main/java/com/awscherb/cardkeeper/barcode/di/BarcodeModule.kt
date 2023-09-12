package com.awscherb.cardkeeper.barcode.di

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.barcode.db.BarcodeDatabase
import com.awscherb.cardkeeper.barcode.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface BarcodeModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(context: Context): BarcodeDatabase {
            return Room.databaseBuilder(
                // using name from original database, hopefully that works
                context.applicationContext, BarcodeDatabase::class.java, "cardkeeper.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideScannedCodeDao(db: BarcodeDatabase) = db.scannedCodeDao()

    }

    @Binds
    abstract fun bindScannedCodeService(handler: ScannedCodeHandler): ScannedCodeService
}