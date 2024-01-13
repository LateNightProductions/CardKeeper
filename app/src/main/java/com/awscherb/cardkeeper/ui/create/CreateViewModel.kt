package com.awscherb.cardkeeper.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val saveResult = MutableStateFlow<SaveResult?>(null)

    fun save(
        title: String,
        text: String,
        format: BarcodeFormat
    ) {
        when {
            title.isBlank() -> saveResult.value = InvalidTitle
            text.isBlank() -> saveResult.value = InvalidText
            else -> {
                val scannedCode = ScannedCodeEntity(
                    title = title,
                    text = text,
                    format = format,
                    created = System.currentTimeMillis(),
                    id = Random.nextInt(),
                    parsedType = ParsedResultType.TEXT
                )

                viewModelScope.launch {
                    try {
                        scannedCodeService.addScannedCode(scannedCode)
                        saveResult.value = SaveSuccess
                    } catch (e: Exception) {
                        saveResult.value = Failure(e)
                    }
                }
            }
        }
    }
}


sealed class SaveResult
object InvalidTitle : SaveResult()
object InvalidText : SaveResult()
object SaveSuccess : SaveResult()
data class Failure(val e: Throwable) : SaveResult()
