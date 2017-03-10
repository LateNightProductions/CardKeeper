package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import rx.Subscription;

public class CardsPresenter implements CardsContract.Presenter {

    private CardsContract.View view;
    private ScannedCodeService scannedCodeService;

    private Subscription listCodesSubscription;
    private Subscription addCodeSubscription;

    public CardsPresenter(CardsContract.View view, ScannedCodeService scannedCodeService) {
        this.view = view;
        this.scannedCodeService = scannedCodeService;

    }

    @Override
    public void loadCards() {
        unsubscribe(listCodesSubscription);

        listCodesSubscription = scannedCodeService.listAllScannedCodes()
                .subscribe(view::showCards,
                        Throwable::printStackTrace);
    }

    @Override
    public void addNewCard(ScannedCode code) {
        unsubscribe(addCodeSubscription);

        addCodeSubscription = scannedCodeService.addScannedCode(code)
                .subscribe(view::onCardAdded,
                        Throwable::printStackTrace);
    }

    @Override
    public void deleteCard(ScannedCode code) {
        scannedCodeService.deleteScannedCode(code)
                .subscribe(deleted -> view.onCardDeleted(),
                        Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe(listCodesSubscription);
        unsubscribe(addCodeSubscription);
    }

    private void unsubscribe(Subscription s) {
        if (s != null) {
            s.unsubscribe();
        }
    }


}
