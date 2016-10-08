package com.awscherb.cardkeeper.dagger.component;

import com.awscherb.cardkeeper.dagger.module.CardsPresenterModule;
import com.awscherb.cardkeeper.ui.cards.CardsFragment;
import com.awscherb.cardkeeper.util.AppScope;

import dagger.Component;

@AppScope
@Component(modules = CardsPresenterModule.class,
        dependencies = ServicesComponent.class)
public interface CardsComponent {
    void inject(CardsFragment fragment);
}
