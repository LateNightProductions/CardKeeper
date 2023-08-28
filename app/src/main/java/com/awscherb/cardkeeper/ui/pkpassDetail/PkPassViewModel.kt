package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.findPassInfo
import com.awscherb.cardkeeper.data.model.getTranslatedLabel
import com.awscherb.cardkeeper.data.service.PkPassService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PkPassViewModel constructor(
    private val pkPassService: PkPassService
) : ViewModel() {

    val passId = MutableStateFlow<String?>(null)

    val pass = passId.filterNotNull()
        .flatMapLatest { pkPassService.getPass(it) }

    val backItems: Flow<List<Pair<String, String>>> = pass.map { pass ->
        val info = pass.findPassInfo()
        if (info?.backFields?.isEmpty() == true) {
            emptyList()
        } else {
            info?.backFields
                ?.filter { it.label != null }
                ?.map { field ->
                    pass.getTranslatedLabel(field.label)!! to
                            field.value
                } ?: emptyList()
        }
    }

    fun deletePass() {
        viewModelScope.launch {
            pkPassService.delete(pass.first())
        }
    }

}