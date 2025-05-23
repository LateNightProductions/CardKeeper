package com.awscherb.cardkeeper.scan

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val pendingCreateData = MutableStateFlow<CreateCodeData?>(null)
    val createResult = MutableStateFlow(false)

    private val adding = MutableStateFlow(false)

    suspend fun createCode(data: CreateCodeData) {
        if (adding.compareAndSet(expect = false, update = true)) {
            scannedCodeService.addScannedCode(
                ScannedCodeEntity(
                    id = Random.nextInt(),
                    format = data.format,
                    text = data.text,
                    title = data.title,
                    created = System.currentTimeMillis(),
                    parsedType = data.parsedResultType
                )
            )
            createResult.value = true
        }
    }
}

data class CreateCodeData(
    val format: BarcodeFormat,
    val text: String,
    val title: String,
    val parsedResultType: ParsedResultType
)