package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.pkpass.model.canBeUpdated
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import com.awscherb.cardkeeper.util.PassWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PkPassViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pkPassService: PkPassService,
    private val passWorkManager: PassWorkManager
) : ViewModel() {

    val passId = savedStateHandle.get<String>("passId")!!

    val pass = pkPassService.getPass(passId)

    val shouldUpdate = pkPassService.shouldAutoUpdatePass(passId)

    init {
        pass
            .combine(shouldUpdate) { pass, update -> (pass.canBeUpdated() && update) to pass }
            .filter { (update, _) -> update }
            .take(1)
            .onEach { (_, pass) ->
                passWorkManager.enqueuePassUpdate(pass)
            }
            .launchIn(viewModelScope)
    }

    val backItems: Flow<List<Pair<String, String>>> = pass.map { pass ->
        val info = pass.findPassInfo()
        if (info?.backFields?.isEmpty() == true) {
            emptyList()
        } else {
            info?.backFields
                ?.filter { it.label != null }
                ?.map { field ->
                    pass.getTranslatedLabel(field.label)!! to
                            pass.getTranslatedValue(field.typedValue)
                } ?: emptyList()
        }
    }

    fun setAutoUpdate(autoUpdate: Boolean) {
        viewModelScope.launch {
            pkPassService.setAutoUpdatePass(passId, autoUpdate)
        }
    }

    fun deletePass() {
        viewModelScope.launch {
            pkPassService.delete(pass.first())
        }
    }

}