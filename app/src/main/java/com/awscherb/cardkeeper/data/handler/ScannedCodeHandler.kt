package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
    var scannedCodeDao: ScannedCodeDao
) : BaseHandler(), ScannedCodeService {

    override suspend fun getScannedCode(codeId: Int): ScannedCode =
        withContext(Dispatchers.Default) {
            scannedCodeDao.getScannedCode(codeId)
        }

    override suspend fun listAllScannedCodes(): List<ScannedCode> =
        withContext(Dispatchers.Default) {
            scannedCodeDao.listScannedCodes()
        }

    override suspend fun addScannedCode(scannedCode: ScannedCode): ScannedCode =
        withContext(Dispatchers.Default) {
            val id = scannedCodeDao.insertCode(scannedCode)
            scannedCode.apply {
                this.id = id.toInt()
            }
        }

    override suspend fun updateScannedCode(scannedCode: ScannedCode): ScannedCode =
        withContext(Dispatchers.Default) {
            scannedCodeDao.updateCode(scannedCode)
            scannedCode
        }

    override suspend fun deleteScannedCode(scannedCode: ScannedCode) =
        withContext(Dispatchers.Default) {
            scannedCodeDao.deleteCode(scannedCode)
        }
}