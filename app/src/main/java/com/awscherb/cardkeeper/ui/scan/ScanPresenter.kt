package com.awscherb.cardkeeper.ui.scan

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ScanPresenter @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val service: ScannedCodeService
) : Presenter<ScanContract.View>(dispatcher), ScanContract.Presenter {

    override fun addNewCode(format: BarcodeFormat, text: String, title: String) {
        val scannedCode = ScannedCode(
            format = format,
            text = text,
            title = title
        )

        uiScope {
            service.addScannedCode(scannedCode)
            view.onCodeAdded()
        }
    }
}