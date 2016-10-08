package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BasePresenter;
import com.awscherb.cardkeeper.ui.base.BaseView;

import java.util.List;

public interface CardsContract {

    interface View extends BaseView<Presenter> {

        void showCards(List<ScannedCode> codes);

    }

    interface Presenter extends BasePresenter {

        void loadCards();

        void addNewCard(ScannedCode code);

        void deleteCard(ScannedCode code);

    }


}
