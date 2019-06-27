package com.awscherb.cardkeeper.data.service

import com.awscherb.cardkeeper.data.model.ScannedCode

interface ScannedCodeService {

    suspend fun getScannedCode(codeId: Int): ScannedCode

    suspend fun listAllScannedCodes(): List<ScannedCode>

    suspend fun addScannedCode(scannedCode: ScannedCode): ScannedCode

    suspend fun updateScannedCode(scannedCode: ScannedCode): ScannedCode

    suspend fun deleteScannedCode(scannedCode: ScannedCode)
}