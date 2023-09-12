package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import javax.inject.Inject

class PkPassViewModelFactory @Inject constructor(
    private val pkPassService: PkPassService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PkPassViewModel(pkPassService) as T
    }
}