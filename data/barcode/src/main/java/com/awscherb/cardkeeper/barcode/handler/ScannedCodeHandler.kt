package com.awscherb.cardkeeper.barcode.handler

import com.awscherb.cardkeeper.barcode.db.ScannedCodeDao
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.core.filterOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
    private val scannedCodeDao: ScannedCodeDao,
) : ScannedCodeService {

    override fun getScannedCode(codeId: Int): Flow<ScannedCodeEntity> {
        return scannedCodeDao.getScannedCode(codeId)
            .filterOne()
    }

    override fun listAll(query: String?): Flow<List<ScannedCodeEntity>> {
        return if (query.isNullOrEmpty()) scannedCodeDao.listScannedCodes() else scannedCodeDao.listScannedCodes(
            query
        )
    }

    override suspend fun addScannedCode(scannedCode: ScannedCodeEntity) {
        scannedCodeDao.insertCode(scannedCode)
    }

    override suspend fun deleteCode(id: Int) {
        scannedCodeDao.deleteCode(id)
    }

    override fun update(item: ScannedCodeModel): Flow<Result<ScannedCodeModel>> {
        return flow<Result<ScannedCodeModel>> {
            withContext(Dispatchers.IO) {
                scannedCodeDao.updateCode(
                    ScannedCodeEntity(
                        id = item.id,
                        format = item.format,
                        title = item.title,
                        text = item.text,
                        created = item.created,
                        parsedType = item.parsedType
                    )
                )
            }
        }
            .flatMapLatest {
                if (it.isSuccess && it.getOrNull()?.id == 1) {
                    getScannedCode(item.id).map { Result.success(it) }
                } else {
                    flowOf(it)
                }
            }
    }

    override suspend fun delete(item: ScannedCodeModel) {
        scannedCodeDao.deleteCode(item.id)
    }
}

