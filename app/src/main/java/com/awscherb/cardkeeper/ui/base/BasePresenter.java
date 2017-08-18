package com.awscherb.cardkeeper.ui.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void onViewDestroyed();
}
