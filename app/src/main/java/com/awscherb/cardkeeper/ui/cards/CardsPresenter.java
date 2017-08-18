package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import io.reactivex.disposables.Disposable;


public class CardsPresenter implements CardsContract.Presenter {

    private CardsContract.View view;
    private ScannedCodeService scannedCodeService;

    private Disposable listCodesSubscription;
    private Disposable addCodeSubscription;

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
                .subscribe(() -> view.onCardDeleted(),
                        Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe(listCodesSubscription);
        unsubscribe(addCodeSubscription);
    }

    private void unsubscribe(Disposable s) {
        if (s != null) {
            s.dispose();
        }
    }


}
