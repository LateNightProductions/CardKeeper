package com.awscherb.cardkeeper.dagger.module;

import com.awscherb.cardkeeper.data.service.ScannedCodeService;
import com.awscherb.cardkeeper.ui.cards.CardsContract;
import com.awscherb.cardkeeper.ui.cards.CardsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CardsPresenterModule {

    private CardsContract.View view;

    public CardsPresenterModule(CardsContract.View v) {
        view = v;
    }

    @Provides
    CardsContract.Presenter providePresenter(ScannedCodeService scannedCodeService) {
        return new CardsPresenter(view, scannedCodeService);
    }
}
