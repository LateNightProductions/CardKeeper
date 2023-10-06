package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.ui.base.CardKeeperActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BindingsModule {


    @ContributesAndroidInjector
    abstract fun contributesCardKeeperActivity(): CardKeeperActivity
}