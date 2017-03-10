package com.awscherb.cardkeeper.dagger.module;

import com.awscherb.cardkeeper.data.service.ScannedCodeService;
import com.awscherb.cardkeeper.ui.card_detail.CardDetailContract;
import com.awscherb.cardkeeper.ui.card_detail.CardDetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CardDetailPresenterModule {

    private CardDetailContract.View view;

    public CardDetailPresenterModule(CardDetailContract.View view) {
        this.view = view;
    }

    @Provides
    CardDetailContract.Presenter providePresenter(ScannedCodeService service) {
        return new CardDetailPresenter(view, service);
    }
}
