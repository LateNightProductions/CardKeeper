package com.awscherb.cardkeeper.barcode.service

import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItemService
import kotlinx.coroutines.flow.Flow

interface ScannedCodeService : SavedItemService<ScannedCodeModel> {

    fun getScannedCode(codeId: Int): Flow<ScannedCodeEntity>

    fun addScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity>

}