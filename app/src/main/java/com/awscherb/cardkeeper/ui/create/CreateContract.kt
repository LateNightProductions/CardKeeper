package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import com.awscherb.cardkeeper.ui.base.BasePresenter
import com.awscherb.cardkeeper.ui.base.BaseView
import com.google.zxing.BarcodeFormat

interface CreateContract {

    interface View : BaseView {
        fun onStateRestored(title: String?, text: String?, format: BarcodeFormat?)
        fun onSaveError(error: SaveError)
        fun onSaveComplete(id: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun setTitle(title: String)
        fun setText(text: String)
        fun setFormat(format: BarcodeFormat)
        fun save()

        fun saveState(bundle: Bundle)
        fun restoreState(bundle: Bundle)
    }
}

sealed class SaveError {
    object InvalidTitle : SaveError()
    object InvalidText : SaveError()
    object InvalidFormat : SaveError()
}