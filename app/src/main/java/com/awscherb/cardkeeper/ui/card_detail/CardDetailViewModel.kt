package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.*
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.create.*
import kotlinx.coroutines.flow.*

class CardDetailViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cardId = MutableStateFlow(-1)
    private val cardState = MutableStateFlow<ScannedCodeEntity?>(null)
    val card = cardState.filterNotNull()

    val title = MutableStateFlow<String?>(null)
    val saveResult = MutableStateFlow<SaveResult?>(null)

    init {
        cardId
            .filter { it != -1 }
            .flatMapLatest {
                scannedCodeService.getScannedCode(it)
            }
            .onEach { cardState.value = it }
            .launchIn(viewModelScope)
    }

    fun save() {
        val titleValue = title.value
        when {
            titleValue.isNullOrEmpty() -> saveResult.value = InvalidTitle
            else -> {

                val scannedCode = cardState.value?.copy(title = titleValue) ?: return

                scannedCodeService.update(scannedCode)
                    .onEach {
                        saveResult.value = when (it.isSuccess) {
                            true -> SaveSuccess(it.getOrThrow().id)
                            false -> Failure(it.exceptionOrNull()!!)
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }
}