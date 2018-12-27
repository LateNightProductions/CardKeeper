package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseActivity
import com.awscherb.cardkeeper.ui.scan.ScanActivity
import kotlinx.android.synthetic.main.activity_cards.*

class CardsActivity : BaseActivity() {

    companion object {
        const val REQUEST_GET_CODE = 3

    }

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        setUpToolbar()
        setTitle("CardKeeper")

        cardsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                    Intent(this, ScanActivity::class.java), REQUEST_GET_CODE)
        }

        insertFragment(CardsFragment.newInstance())

    }


}
