package com.awscherb.cardkeeper.ui.card_detail;

import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class CardDetailPresenter implements CardDetailContract.Presenter {

    private CardDetailContract.View view;

    @Inject ScannedCodeService service;
    private Disposable loadSubscription;

    //================================================================================
    // Constructor
    //================================================================================

    @Inject
    public CardDetailPresenter() {


    }

    //================================================================================
    // Presenter methods
    //================================================================================


    @Override
    public void attachView(CardDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void loadCard(int id) {
        unsubscribe(loadSubscription);

        loadSubscription = service.getScannedCode(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCard,
                        Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe(loadSubscription);
    }

    private void unsubscribe(Disposable s) {
        if (s != null) {
            s.dispose();
        }
    }

}
