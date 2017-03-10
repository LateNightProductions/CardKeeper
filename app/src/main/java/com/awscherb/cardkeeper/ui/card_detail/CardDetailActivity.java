package com.awscherb.cardkeeper.ui.card_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.ui.base.BaseActivity;

public class CardDetailActivity extends BaseActivity {

    private static final String TAG = CardDetailActivity.class.getName();

    public static final String EXTRA_CARD_ID = TAG + ".extra_card_id";

    //================================================================================
    // Lifecycle methods
    //================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        setUpToolbar();

        insertFragment(CardDetailFragment.newInstance(getIntent().getLongExtra(EXTRA_CARD_ID, 0)));

    }

}
