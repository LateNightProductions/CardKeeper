package com.awscherb.cardkeeper.pkpass.handler

import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import com.awscherb.cardkeeper.core.filterOne
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.awscherb.cardkeeper.pkpass.entity.PassUpdateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PkPassHandler @Inject constructor(
    private val dao: PkPassDao,
) : PkPassService {

    override fun getPass(passId: String): Flow<PkPassModel> {
        return dao.getPass(passId).filterOne()
    }

    override fun listAll(query: String?): Flow<List<PkPassModel>> {
        return if (query.isNullOrEmpty()) dao.listPkPasses() else dao.listPkPasses(query)
    }

    override suspend fun delete(item: PkPassModel) {
        dao.delete(item.id)
    }

    override fun update(item: PkPassModel): Flow<Result<PkPassModel>> {
        TODO("Not yet implemented")
    }

    override fun shouldAutoUpdatePass(passId: String): Flow<Boolean> {
        return dao.getUpdateSettingsForPass(passId)
            .map {
                if (it.isEmpty()) {
                    false
                } else {
                    it.first().shouldAutoUpdate
                }
            }
    }

    override suspend fun setAutoUpdatePass(passId: String, autoUpdate: Boolean) {
        dao.setAutoUpdateSettings(
            PassUpdateEntity(
                passId = passId, shouldAutoUpdate = autoUpdate
            )
        )
    }
}