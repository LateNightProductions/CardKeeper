package com.awscherb.cardkeeper.ui.scan

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import com.google.zxing.BarcodeFormat
import io.reactivex.Scheduler
import javax.inject.Inject

class ScanPresenter @Inject constructor(
    uiScheduler: Scheduler,
    private val service: ScannedCodeService
) : Presenter<ScanContract.View>(uiScheduler), ScanContract.Presenter {

    override fun addNewCode(format: BarcodeFormat, text: String, title: String) {
        val scannedCode = ScannedCode().apply {
            this.format = format
            this.text = text
            this.title = title
        }
        addDisposable(
            service.addScannedCode(scannedCode)
                .compose(scheduleSingle())
                .subscribe(
                    { view?.onCodeAdded() },
                    { view?.onError(it) }
                )
        )
    }
}