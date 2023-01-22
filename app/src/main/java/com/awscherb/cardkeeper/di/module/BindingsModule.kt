package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.ui.base.CardKeeperActivity
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment
import com.awscherb.cardkeeper.ui.create.CreateFragment
import com.awscherb.cardkeeper.ui.items.ItemsFragment
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingsModule {

    @ContributesAndroidInjector
    abstract fun contributesCardDetailFragment(): CardDetailFragment

    @ContributesAndroidInjector
    abstract fun contributesItemsFragment(): ItemsFragment

    @ContributesAndroidInjector
    abstract fun contributesCreateFragment(): CreateFragment

    @ContributesAndroidInjector
    abstract fun contributesScanFragment(): ScanFragment

    @ContributesAndroidInjector
    abstract fun contributesCardKeeperActivity(): CardKeeperActivity
}