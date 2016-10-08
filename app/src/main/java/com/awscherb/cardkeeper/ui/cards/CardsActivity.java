package com.awscherb.cardkeeper.ui.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.ui.base.BaseActivity;

public class CardsActivity extends BaseActivity {

    //================================================================================
    // Lifecycle methods
    //================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(t);
        setTitle("CardKeeper");

        insertFragment(CardsFragment.newInstance());

    }


}
