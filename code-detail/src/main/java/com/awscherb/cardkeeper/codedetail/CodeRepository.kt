package com.awscherb.cardkeeper.codedetail

import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CodeRepository @Inject constructor(
    private val scannedCodeService: ScannedCodeService
) {

    fun fetchCode(id: Int): Flow<CodeDetailModel> {
        return scannedCodeService.getScannedCode(id)
            .map {
                CodeDetailModel(
                    title = it.title,
                    text = it.text,
                    format = it.format,
                    parsedType = it.parsedType
                )
            }
    }

    suspend fun delete(id: Int) {
        scannedCodeService.deleteCode(id)
    }
}