package com.awscherb.cardkeeper.items.repo

import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.GroupedPassItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel
import com.awscherb.cardkeeper.items.util.Mappers
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val scannedCodeService: ScannedCodeService,
    private val pkPassService: PkPassService
) {

    fun listSavedItems(
        query: String? = null,
    ): Flow<List<ItemModel>> {
        return combine(
            scannedCodeService.listAll(query),
            pkPassService.listAll(query)
        ) { codes, passes ->
            codes.map(Mappers::scannedCodeItemModel) + groupPasses(passes)
        }.flowOn(Dispatchers.IO)
    }

    private fun groupPasses(passes: List<PkPassModel>): List<ItemModel> {
        return passes
            .groupBy { it.groupingIdentifier }
            .flatMap { (groupId, group) ->
                val mapped = group.map(Mappers::passItemModel)
                if (groupId != null && mapped.size > 1) {
                    listOf(
                        GroupedPassItemModel(
                            id = groupId,
                            passes = mapped,
                            created = group.maxOf { it.created },
                            sortOrder = group.maxOf { it.sortOrder }
                        )
                    )
                } else {
                    mapped
                }
            }
    }

    suspend fun updateSortOrders(items: List<ItemModel>) = withContext(Dispatchers.IO) {
        items.forEachIndexed { index, item ->
            val sortOrder = (items.size - index).toLong() * 1000L
            when (item) {
                is ScannedCodeItemModel -> scannedCodeService.updateSortOrder(item.id.toInt(), sortOrder)
                is PassItemModel -> pkPassService.updateSortOrder(item.id, sortOrder)
                is GroupedPassItemModel -> item.passes.forEach { pkPassService.updateSortOrder(it.id, sortOrder) }
            }
        }
    }

}