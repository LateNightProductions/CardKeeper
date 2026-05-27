package com.awscherb.cardkeeper.passdetail

import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.util.Mappers
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PassDetailRepository @Inject constructor(
    private val passService: PkPassService
) {

    fun getPass(passId: String) = passService.getPass(passId).map {
        Mappers.detailModel(it)
    }

    fun shouldAutoUpdate(passId: String) = passService.shouldAutoUpdatePass(passId)

    suspend fun setAutoUpdate(
        passId: String,
        autoUpdate: Boolean
    ) {
        passService.setAutoUpdatePass(passId, autoUpdate)
    }

    fun getPassesByGroupingIdentifier(groupingIdentifier: String): Flow<List<PassDetailModel>> =
        passService.getPassesByGroupingIdentifier(groupingIdentifier).map { passes ->
            passes.map(Mappers::detailModel)
        }

    fun getPassesByGroupId(groupId: String): Flow<List<PassDetailModel>> =
        passService.getPassesByGroupId(groupId).map { passes ->
            passes.map(Mappers::detailModel)
        }

    suspend fun delete(id: String) {
        passService.delete(id)
    }

}