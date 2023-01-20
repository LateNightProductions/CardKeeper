package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.awscherb.cardkeeper.data.service.SavedItemService
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
    private val scannedCodeDao: ScannedCodeDao
) : ScannedCodeService, SavedItemService<ScannedCodeModel> {

    override fun getScannedCode(codeId: Int): Flow<ScannedCodeEntity> {
        return scannedCodeDao.getScannedCode(codeId)
    }

    override fun listAll(query: String?): Flow<List<ScannedCodeEntity>> {
        return if (query == null) scannedCodeDao.listScannedCodes() else scannedCodeDao.listScannedCodes(query)
    }

    override fun addScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity> {
        return flow {
            val id = scannedCodeDao.insertCode(scannedCode)
            emit(id)
        }
            .flatMapLatest { scannedCodeDao.getScannedCode(it.toInt()) }
    }

    override fun updateScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity> {
        return flow {
            scannedCodeDao.updateCode(scannedCode)
            emit(scannedCode)
        }
    }

    override suspend fun delete(item: ScannedCodeModel) {
        scannedCodeDao.deleteCode(item.id)
    }
}