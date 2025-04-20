package com.awscherb.cardkeeper.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.types.BarcodeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createRepository: CreateRepository
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
                val createModel = CreateModel(
                    title = title,
                    text = text,
                    format = format,
                )

                viewModelScope.launch {
                    createRepository.create(createModel)
                        .onSuccess {
                            saveResult.value = SaveSuccess
                        }
                        .onFailure {
                            saveResult.value = Failure(it)
                        }
                }
            }
        }
    }
}
