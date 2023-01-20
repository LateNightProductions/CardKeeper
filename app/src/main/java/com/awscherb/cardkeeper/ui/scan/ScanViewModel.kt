package com.awscherb.cardkeeper.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.google.zxing.BarcodeFormat

class ScanViewModel(
    scannedCodeService: ScannedCodeService
) : ViewModel() {

    val createData = MutableLiveData<CreateCodeData>()

    val createResult: LiveData<Unit> = createData.switchMap {
        scannedCodeService.addScannedCode(
            ScannedCodeEntity(
                format = it.format,
                text = it.text,
                title = it.title,
                created = System.currentTimeMillis()
            )
        ).asLiveData(viewModelScope.coroutineContext)
    }.map { }
}

data class CreateCodeData(
    val format: BarcodeFormat,
    val text: String,
    val title: String
)