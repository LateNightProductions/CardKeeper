package com.awscherb.cardkeeper.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}