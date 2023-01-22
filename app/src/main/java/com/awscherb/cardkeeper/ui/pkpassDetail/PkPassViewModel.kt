package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.service.PkPassService
import kotlinx.coroutines.flow.MutableStateFlow

class PkPassViewModel constructor(
    private val pkPassService: PkPassService
) : ViewModel() {

    val pass = MutableStateFlow<PkPassModel?>(null)

}