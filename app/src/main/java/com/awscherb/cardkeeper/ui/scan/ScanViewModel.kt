package com.awscherb.cardkeeper.ui.scan

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class ScanViewModel(
    scannedCodeService: ScannedCodeService
) : ViewModel() {

    val createData = MutableStateFlow<CreateCodeData?>(null)

    val createResult: Flow<Unit> = createData
        .filterNotNull()
        .flatMapLatest {
            scannedCodeService.addScannedCode(
                ScannedCodeEntity(
                    format = it.format,
                    text = it.text,
                    title = it.title,
                    created = System.currentTimeMillis()
                )
            )
        }.map { }
}

data class CreateCodeData(
    val format: BarcodeFormat,
    val text: String,
    val title: String
)