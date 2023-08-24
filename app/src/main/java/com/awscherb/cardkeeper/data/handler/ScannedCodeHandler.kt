package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.awscherb.cardkeeper.data.service.SavedItemService
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.util.extensions.filterOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
    private val scannedCodeDao: ScannedCodeDao,
) : ScannedCodeService, SavedItemService<ScannedCodeModel> {

    override fun getScannedCode(codeId: Int): Flow<ScannedCodeEntity> {
        return scannedCodeDao.getScannedCode(codeId)
            .filterOne()
    }

    override fun listAll(query: String?): Flow<List<ScannedCodeEntity>> {
        return if (query == null) scannedCodeDao.listScannedCodes() else scannedCodeDao.listScannedCodes(
            query
        )
    }


    override fun addScannedCode(scannedCode: ScannedCodeEntity): Flow<ScannedCodeEntity> {
        return flow {
            val id = scannedCodeDao.insertCode(scannedCode)
            emit(id)
        }
            .flatMapLatest {
                scannedCodeDao.getScannedCode(it.toInt())
                    .filterOne()
            }
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
                        created = item.created
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

