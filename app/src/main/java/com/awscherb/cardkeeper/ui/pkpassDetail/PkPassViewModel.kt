package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PkPassViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pkPassService: PkPassService
) : ViewModel() {

    val passId = savedStateHandle.get<String>("passId")!!

    val pass = pkPassService.getPass(passId)

    val backItems: Flow<List<Pair<String, String>>> = pass.map { pass ->
        val info = pass.findPassInfo()
        if (info?.backFields?.isEmpty() == true) {
            emptyList()
        } else {
            info?.backFields
                ?.filter { it.label != null }
                ?.map { field ->
                    pass.getTranslatedLabel(field.label)!! to
                            pass.getTranslatedValue(field.value)
                } ?: emptyList()
        }
    }

    fun deletePass() {
        viewModelScope.launch {
            pkPassService.delete(pass.first())
        }
    }

}