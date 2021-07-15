package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
    private val scannedCodeDao: ScannedCodeDao
) : ScannedCodeService {

    override fun getScannedCode(codeId: Int): Flow<ScannedCode> {
        return scannedCodeDao.getScannedCode(codeId)
    }

    override fun listAllScannedCodes(query: String?): Flow<List<ScannedCode>> {
        return if (query == null) scannedCodeDao.listScannedCodes() else scannedCodeDao.listScannedCodes(query)
    }

    override fun addScannedCode(scannedCode: ScannedCode): Flow<ScannedCode> {
        return flow {
            val id = scannedCodeDao.insertCode(scannedCode)
            scannedCode.apply {
                this.id = id.toInt()
            }
            emit(scannedCode)
        }

    }

    override fun updateScannedCode(scannedCode: ScannedCode): Flow<ScannedCode> {
        return flow {
            scannedCodeDao.updateCode(scannedCode)
            emit(scannedCode)
        }
    }

    override fun deleteScannedCode(scannedCode: ScannedCode): Flow<Unit> {
        return flow {
            scannedCodeDao.deleteCode(scannedCode)
            emit(Unit)
        }
    }
}