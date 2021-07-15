package com.awscherb.cardkeeper.data.service

import com.awscherb.cardkeeper.data.model.ScannedCode
import kotlinx.coroutines.flow.Flow

interface ScannedCodeService {

    fun getScannedCode(codeId: Int): Flow<ScannedCode>

    fun listAllScannedCodes(query: String? = null): Flow<List<ScannedCode>>

    fun addScannedCode(scannedCode: ScannedCode): Flow<ScannedCode>

    fun updateScannedCode(scannedCode: ScannedCode): Flow<ScannedCode>

    fun deleteScannedCode(scannedCode: ScannedCode): Flow<Unit>
}