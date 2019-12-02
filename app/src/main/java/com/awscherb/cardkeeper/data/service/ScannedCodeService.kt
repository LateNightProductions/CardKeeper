package com.awscherb.cardkeeper.data.service

import com.awscherb.cardkeeper.data.model.ScannedCode
import kotlinx.coroutines.flow.Flow

interface ScannedCodeService {

    suspend fun getScannedCode(codeId: Int): ScannedCode

    fun listAllScannedCodes(): Flow<List<ScannedCode>>

    suspend fun addScannedCode(scannedCode: ScannedCode): ScannedCode

    suspend fun updateScannedCode(scannedCode: ScannedCode): ScannedCode

    suspend fun deleteScannedCode(scannedCode: ScannedCode)
}