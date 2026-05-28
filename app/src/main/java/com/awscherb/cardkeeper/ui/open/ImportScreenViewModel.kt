package com.awscherb.cardkeeper.ui.open

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.awscherb.cardkeeper.util.ImportWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ImportScreenViewModel @Inject constructor(
    private val importWorkManager: ImportWorkManager
) : ViewModel() {

    val importResult = MutableStateFlow<ImportResult?>(null)

    fun startImport(uris: List<Uri>) {
        val total = uris.size
        val (_, flow) = importWorkManager.enqueueUris(uris)
        flow.onEach { infos ->
            val finished = infos.filter { it.state.isFinished }
            val succeeded = finished.count { it.state == WorkInfo.State.SUCCEEDED }
            val failed = finished.count { it.state == WorkInfo.State.FAILED }

            importResult.value = when {
                finished.size < total -> ImportResult.InProgress(succeeded, total)
                failed == total -> ImportResult.Error("There was an error importing your passes")
                failed > 0 -> ImportResult.PartialSuccess(failed)
                else -> ImportResult.Success
            }
        }.launchIn(viewModelScope)
    }
}

sealed class ImportResult {
    data object Success : ImportResult()
    data class InProgress(val imported: Int, val total: Int) : ImportResult()
    data class PartialSuccess(val failed: Int) : ImportResult()
    data class Error(val message: String) : ImportResult()
}