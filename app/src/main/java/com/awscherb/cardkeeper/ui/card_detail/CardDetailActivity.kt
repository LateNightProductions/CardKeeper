package com.awscherb.cardkeeper.ui.card_detail

import android.os.Bundle

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseActivity

class CardDetailActivity : BaseActivity() {

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        setUpToolbar()
        setupToolbarBack()

        insertFragment(CardDetailFragment.newInstance(intent.getIntExtra(EXTRA_CARD_ID, 0)))

    }

    companion object {
        private const val TAG = "CardDetailActivity"
        const val EXTRA_CARD_ID = "$TAG.extra_card_id"
    }

}
