package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BasePresenter;
import com.awscherb.cardkeeper.ui.base.BaseView;

import java.util.List;

public interface CardsContract {

    interface View extends BaseView {

        void showCards(List<ScannedCode> codes);

        void onCardAdded(ScannedCode code);

        void onCardDeleted();

    }

    interface Presenter extends BasePresenter<CardsContract.View> {

        void loadCards();

        void addNewCard(ScannedCode code);

        void deleteCard(ScannedCode code);

    }


}
