package com.awscherb.cardkeeper.ui.scan

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BasePresenter
import com.awscherb.cardkeeper.ui.base.BaseView
import com.google.zxing.BarcodeFormat

interface ScanContract {

    interface View : BaseView {
        fun onCodeAdded()
    }

    interface Presenter : BasePresenter<View> {
        fun addNewCode(format: BarcodeFormat, text: String, title: String)
    }
}