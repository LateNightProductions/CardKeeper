package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.api.PkPassApi
import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.canBeUpdated
import com.awscherb.cardkeeper.data.service.PkPassService
import com.awscherb.cardkeeper.util.extensions.filterOne
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PkPassHandler @Inject constructor(
    private val dao: PkPassDao,
) : PkPassService {

    override fun getPass(passId: String): Flow<PkPassModel> {
        return dao.getPass(passId).filterOne()
    }

    override fun listAll(query: String?): Flow<List<PkPassModel>> {
        return if (query == null) dao.listPkPasses() else dao.listPkPasses(query)
    }

    override suspend fun delete(item: PkPassModel) {
        dao.delete(item.id)
    }

    override fun update(item: PkPassModel): Flow<Result<PkPassModel>> {
        TODO("Not yet implemented")
    }
}