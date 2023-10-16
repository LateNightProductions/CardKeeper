package com.awscherb.cardkeeper.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CreateViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val title = MutableStateFlow<String?>(null)

    val text = MutableStateFlow<String?>(null)

    val format = MutableStateFlow<CreateType?>(null)

    val saveResult = MutableStateFlow<SaveResult?>(null)

    fun save() {
        val titleValue = title.value
        val textValue = text.value
        val formatValue = format.value
        when {
            titleValue.isNullOrEmpty() -> saveResult.value = InvalidTitle
            textValue.isNullOrEmpty() -> saveResult.value = InvalidText
            formatValue == null -> saveResult.value = InvalidFormat
            else -> {
                val scannedCode = ScannedCodeEntity(
                    title = titleValue,
                    text = textValue,
                    format = formatValue.format,
                    created = System.currentTimeMillis(),
                    id = 0
                )

                // scannedCodeService.addScannedCode(scannedCode)
                //     .onEach {
                //         saveResult.value = SaveSuccess(it.id)
                //     }.launchIn(viewModelScope)
            }
        }
    }
}


sealed class SaveResult
object InvalidTitle : SaveResult()
object InvalidText : SaveResult()
object InvalidFormat : SaveResult()
data class SaveSuccess(val codeId: Int) : SaveResult()
data class Failure(val e: Throwable) : SaveResult()
