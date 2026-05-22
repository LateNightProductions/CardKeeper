package com.awscherb.cardkeeper.barcode.service

import com.awscherb.cardkeeper.barcode.model.NewScannedCode
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItemService
import kotlinx.coroutines.flow.Flow

interface ScannedCodeService : SavedItemService<ScannedCodeModel> {

    fun getScannedCode(codeId: Int): Flow<ScannedCodeModel>

    suspend fun addScannedCode(code: NewScannedCode)

    suspend fun deleteCode(id: Int)
}