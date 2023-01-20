package com.awscherb.cardkeeper.data.service

import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import kotlinx.coroutines.flow.Flow

interface ScannedCodeService : SavedItemService<ScannedCodeModel> {

    fun getScannedCode(codeId: Int): Flow<ScannedCodeEntity>

    fun addScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity>

    fun updateScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity>

}