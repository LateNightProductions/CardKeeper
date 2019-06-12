package com.awscherb.cardkeeper.ui.base

import android.os.Bundle
import com.awscherb.cardkeeper.R

class CardKeeperActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_keeper)
    }

}