package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.data.CardKeeperDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule {
    @Provides fun provideScannedCodeDao(db: CardKeeperDatabase) = db.scannedCodeDao()
}