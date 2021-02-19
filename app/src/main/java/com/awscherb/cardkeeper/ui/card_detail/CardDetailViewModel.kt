package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.*
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.create.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CardDetailViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cardId = MutableLiveData<Int>()

    val card: LiveData<ScannedCode>

    val title = MutableLiveData<String>()
    val saveResult = MutableLiveData<SaveResult>()

    init {
        card = cardId.switchMap {
            scannedCodeService.getScannedCode(it).asLiveData(viewModelScope.coroutineContext)
        }
    }

    fun save() {
        val titleValue = title.value
        when {
            titleValue.isNullOrEmpty() -> saveResult.postValue(InvalidTitle)
            else -> {
                val scannedCode = card.value?.copy(title = titleValue) ?: return

                scannedCodeService.updateScannedCode(scannedCode)
                    .onEach {
                        saveResult.postValue(SaveSuccess(it.id))
                    }.launchIn(viewModelScope)
            }
        }
    }
}