package com.awscherb.cardkeeper.passdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.passdetail.worker.PassWorkManager
import com.awscherb.cardkeeper.pkpass.service.PkPassService
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
class PkPassViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pkPassService: PkPassService,
    private val passWorkManager: PassWorkManager
) : ViewModel() {

    val passId = savedStateHandle.get<String>("passId")!!

    val pass: Flow<PassDetailModel> = pkPassService.getPass(passId).map {
        Mappers.detailModel(it)
    }

    val shouldUpdate = pkPassService.shouldAutoUpdatePass(passId)

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
//        if (info?.backFields?.isEmpty() == true) {
//            emptyList()
//        } else {
//            info?.backFields
//                ?.filter { it.label != null }
//                ?.map { field ->
//                    pass.getTranslatedLabel(field.label)!! to
//                            pass.getTranslatedValue(field.typedValue)
//                } ?: emptyList()
//        }
    }

    fun setAutoUpdate(autoUpdate: Boolean) {
        viewModelScope.launch {
            pkPassService.setAutoUpdatePass(passId, autoUpdate)
        }
    }

    fun deletePass() {
        viewModelScope.launch {
//            pkPassService.delete(pass.first())
        }
    }

}