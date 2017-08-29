package com.awscherb.cardkeeper.ui.scan

import android.os.Bundle

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseActivity

class ScanActivity : BaseActivity() {

    // ========================================================================
    // Lifecycle methods
    // ========================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scan)
        insertFragment(ScanFragment())
    }
}
