package com.awscherb.cardkeeper.codedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannedCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CodeRepository
) : ViewModel() {

    private val codeId = savedStateHandle.get<Int>("codeId")!!
    val code = repository.fetchCode(codeId)

    suspend fun delete() {
        repository.delete(codeId)
    }
}