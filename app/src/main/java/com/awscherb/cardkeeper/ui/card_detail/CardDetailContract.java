package com.awscherb.cardkeeper.ui.card_detail;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BasePresenter;
import com.awscherb.cardkeeper.ui.base.BaseView;

public interface CardDetailContract {

    interface View extends BaseView {

        void showCard(ScannedCode code);

    }

    interface Presenter extends BasePresenter {

        void loadCard(long id);

    }

}
