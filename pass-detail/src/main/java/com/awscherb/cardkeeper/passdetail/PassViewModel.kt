package com.awscherb.cardkeeper.passdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.worker.PassWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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

    val passes = repository.getPass(passId)
        .flatMapLatest { initial ->
            val groupId = initial.groupingIdentifier
            if (groupId != null) {
                repository.getPassesByGroupingIdentifier(groupId)
            } else {
                flowOf(listOf(initial))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val currentPassIndex = MutableStateFlow(0)

    val shouldUpdate = repository.shouldAutoUpdate(passId)

    init {
        passes
            .map { it.firstOrNull() }
            .filter { it != null }
            .combine(shouldUpdate) { pass, update -> (pass!!.canBeUpdated && update) to pass }
            .filter { (update, _) -> update }
            .take(1)
            .onEach { (_, pass) -> passWorkManager.enqueuePassUpdate(pass) }
            .launchIn(viewModelScope)
    }

    val backItems = combine(passes, currentPassIndex) { passes, index ->
        passes.getOrNull(index)?.backItems ?: emptyList()
    }

    fun setAutoUpdate(autoUpdate: Boolean) {
        viewModelScope.launch {
            repository.setAutoUpdate(passId, autoUpdate)
        }
    }

    fun deletePass() {
        viewModelScope.launch {
            val pass = passes.value.getOrNull(currentPassIndex.value) ?: return@launch
            repository.delete(pass.id)
        }
    }

}
