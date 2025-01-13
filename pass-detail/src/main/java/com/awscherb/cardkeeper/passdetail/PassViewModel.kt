package com.awscherb.cardkeeper.passdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.worker.PassWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PassDetailRepository,
    private val passWorkManager: PassWorkManager
) : ViewModel() {

    private val passId = savedStateHandle.get<String>("passId")!!

    val pass: Flow<PassDetailModel> = repository.getPass(passId)

    val shouldUpdate = repository.shouldAutoUpdate(passId)

    init {
        pass
            .combine(shouldUpdate) { pass, update -> (pass.canBeUpdated && update) to pass }
            .filter { (update, _) -> update }
            .take(1)
            .onEach { (_, pass) ->
                passWorkManager.enqueuePassUpdate(pass)
            }
            .launchIn(viewModelScope)
    }

    val backItems: Flow<List<Pair<String, String>>> = pass.map { pass ->
        pass.backItems
    }

    fun setAutoUpdate(autoUpdate: Boolean) {
        viewModelScope.launch {
            repository.setAutoUpdate(passId, autoUpdate)
        }
    }

    fun deletePass() {
        viewModelScope.launch {
            repository.delete(passId)
        }
    }

}