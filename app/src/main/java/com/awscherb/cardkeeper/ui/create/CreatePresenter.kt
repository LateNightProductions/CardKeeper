package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import android.os.Parcelable
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import com.google.zxing.BarcodeFormat
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreatePresenter @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val scannedCodeService: ScannedCodeService
) : Presenter<CreateContract.View>(dispatcher),
    CreateContract.Presenter {

    private var state: PresenterState = PresenterState()

    override fun setTitle(title: String) {
        state = state.copy(title = title)
    }

    override fun setText(text: String) {
        state = state.copy(text = text)
    }

    override fun setFormat(format: BarcodeFormat) {
        state = state.copy(format = format)
    }

    override fun save() {
        if (state.title.isNullOrEmpty()) {
            view.onSaveError(SaveError.InvalidTitle)
        } else if (state.text.isNullOrEmpty()) {
            view.onSaveError(SaveError.InvalidText)
        } else if (state.format == null) {
            view.onSaveError(SaveError.InvalidFormat)
        } else {
            uiScope {
                view.onSaveComplete(scannedCodeService.addScannedCode(ScannedCode().apply {
                    this.format = state.format!!
                    this.text = state.text!!
                    this.title = state.title!!
                }).id)
            }
        }
    }

    override fun saveState(bundle: Bundle) {
//        bundle.putParcelable(EXTRA_STATE, state as Parcelable)
    }

    override fun restoreState(bundle: Bundle) {
        this.state = bundle.getParcelable(EXTRA_STATE) as? PresenterState ?: PresenterState()
        view.onStateRestored(
            title = state.title,
            text = state.text,
            format = state.format
        )
    }

    companion object {
        private const val TAG = "CreatePresenter"
        private const val EXTRA_STATE = "$TAG.state"
    }
}

@Parcelize
private data class PresenterState(
    val title: String? = null,
    val text: String? = null,
    val format: BarcodeFormat? = null
) : Parcelable