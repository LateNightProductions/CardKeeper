package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.service.PkPassService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

class PkPassViewModel constructor(
    private val pkPassService: PkPassService
) : ViewModel() {

    val passId = MutableStateFlow<String?>(null)

    val pass = passId.filterNotNull()
        .flatMapLatest { pkPassService.getPass(it) }


}