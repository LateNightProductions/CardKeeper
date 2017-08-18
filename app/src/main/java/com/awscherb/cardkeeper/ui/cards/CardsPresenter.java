package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class CardsPresenter implements CardsContract.Presenter {

    private CardsContract.View view;
    @Inject ScannedCodeService scannedCodeService;

    private Disposable listCodesSubscription;
    private Disposable addCodeSubscription;

    @Inject public CardsPresenter() {

    }

    @Override
    public void attachView(CardsContract.View view) {
        this.view = view;
    }

    @Override
    public void loadCards() {
        unsubscribe(listCodesSubscription);

        listCodesSubscription = scannedCodeService.listAllScannedCodes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCards,
                        Throwable::printStackTrace);
    }

    @Override
    public void addNewCard(ScannedCode code) {
        unsubscribe(addCodeSubscription);

        addCodeSubscription = scannedCodeService.addScannedCode(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onCardAdded,
                        Throwable::printStackTrace);
    }

    @Override
    public void deleteCard(ScannedCode code) {
        scannedCodeService.deleteScannedCode(code)
                .observeOn(AndroidSchedulers.mainThread())
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
