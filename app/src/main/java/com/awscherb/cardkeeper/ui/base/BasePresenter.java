package com.awscherb.cardkeeper.ui.base;

public interface BasePresenter<T extends BaseView> {
    void onViewDestroyed();
}
