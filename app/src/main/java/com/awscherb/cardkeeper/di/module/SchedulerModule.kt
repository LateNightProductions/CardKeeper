package com.awscherb.cardkeeper.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named

@Module
class SchedulerModule {
    @Provides
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}