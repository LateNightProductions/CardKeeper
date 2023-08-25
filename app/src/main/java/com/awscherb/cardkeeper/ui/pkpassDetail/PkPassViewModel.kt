package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.service.PkPassService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PkPassViewModel constructor(
    private val pkPassService: PkPassService
) : ViewModel() {

    val passId = MutableStateFlow<String?>(null)

    val pass = passId.filterNotNull()
        .flatMapLatest { pkPassService.getPass(it) }

    fun deletePass() {
        viewModelScope.launch {
            pkPassService.delete(pass.first())
        }
    }

}