package com.awscherb.cardkeeper.ui.card_detail;

import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import io.reactivex.disposables.Disposable;


public class CardDetailPresenter implements CardDetailContract.Presenter {

    private CardDetailContract.View view;

    private ScannedCodeService service;
    private Disposable loadSubscription;

    //================================================================================
    // Constructor
    //================================================================================

    public CardDetailPresenter(CardDetailContract.View view, ScannedCodeService service) {
        this.view = view;
        this.service = service;
    }

    //================================================================================
    // Presenter methods
    //================================================================================

    @Override
    public void loadCard(long id) {
        unsubscribe(loadSubscription);

        loadSubscription = service.getScannedCode(id)
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
