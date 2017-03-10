package com.awscherb.cardkeeper.dagger.component;

import com.awscherb.cardkeeper.dagger.module.CardDetailPresenterModule;
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment;
import com.awscherb.cardkeeper.util.AppScope;

import dagger.Component;

@AppScope
@Component(modules = CardDetailPresenterModule.class,
        dependencies = ServicesComponent.class)
public interface CardDetailComponent {
    void inject(CardDetailFragment view);
}
