package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.data.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {
    @Binds abstract fun bindScannedCodeService(handler: ScannedCodeHandler): ScannedCodeService
}