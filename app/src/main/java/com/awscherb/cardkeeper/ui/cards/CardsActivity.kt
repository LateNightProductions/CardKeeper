package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import butterknife.BindView
import butterknife.ButterKnife

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseActivity
import com.awscherb.cardkeeper.ui.scan.ScanActivity

class CardsActivity : BaseActivity() {

    companion object {
        const val REQUEST_GET_CODE = 3

    }

    @BindView(R.id.fragment_cards_fab) internal lateinit var fab: FloatingActionButton

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        ButterKnife.bind(this)

        setUpToolbar()
        setTitle("CardKeeper")

        fab.setOnClickListener {
            startActivityForResult(
                    Intent(this, ScanActivity::class.java), REQUEST_GET_CODE)
        }

        insertFragment(CardsFragment.newInstance())


    }


}
