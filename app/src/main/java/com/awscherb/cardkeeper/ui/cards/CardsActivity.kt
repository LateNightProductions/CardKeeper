package com.awscherb.cardkeeper.ui.cards

import android.os.Bundle

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseActivity

class CardsActivity : BaseActivity() {

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        setUpToolbar()
        setTitle("CardKeeper")

        insertFragment(CardsFragment.newInstance())

    }


}
