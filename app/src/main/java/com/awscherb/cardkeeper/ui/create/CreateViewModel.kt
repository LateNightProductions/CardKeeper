package com.awscherb.cardkeeper.ui.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.launch

class CreateViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val title = MutableLiveData<String>()

    val text = MutableLiveData<String>()

    val format = MutableLiveData<CreateType>()

    val saveResult = MutableLiveData<SaveResult>()

    fun save() {
        val titleValue = title.value
        val textValue = text.value
        val formatValue = format.value
        when {
            titleValue.isNullOrEmpty() -> saveResult.postValue(InvalidTitle)
            textValue.isNullOrEmpty() -> saveResult.postValue(InvalidText)
            formatValue == null -> saveResult.postValue(InvalidFormat)
            else -> {
                val scannedCode = ScannedCode(
                    title = titleValue,
                    text = textValue,
                    format = formatValue.format
                )

                viewModelScope.launch {
                    saveResult.postValue(
                        SaveSuccess(
                            scannedCodeService.addScannedCode(scannedCode).id
                        )
                    )
                }
            }
        }
    }
}


sealed class SaveResult
object InvalidTitle : SaveResult()
object InvalidText : SaveResult()
object InvalidFormat : SaveResult()
data class SaveSuccess(val codeId: Int) : SaveResult()
