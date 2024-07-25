package com.awscherb.cardkeeper.ui.open

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.ui.base.Destination
import com.awscherb.cardkeeper.util.ImportWorkManager
import com.google.android.material.snackbar.Snackbar
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


    fun startImport(uri: Uri) {
        importWorkManager.enqueueContentUri(uri)
            .onEach {

                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        it.outputData.getString(ImportPassWorker.KEY_PASS_ID)
                            ?.let { passId ->
                                importResult.value = ImportResult.Success
                            }
                    }

                    WorkInfo.State.FAILED -> {
                        importResult.value = ImportResult.Error("There was an error")
                    }

                    else -> {
                    }
                }
            }.launchIn(viewModelScope)
    }
}

sealed class ImportResult {
    data object Success : ImportResult()
    data class Error(val message: String) : ImportResult()
}