package com.awscherb.cardkeeper.items

import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SavedItemRepository @Inject constructor(
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
            (codes.map(Mappers::scannedCodeItemModel) + passes.map(Mappers::passItemModel))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteItem(item: SavedItem) {
        when (item) {
            is PkPassModel -> pkPassService.delete(item)
            is ScannedCodeModel -> scannedCodeService.delete(item)
        }
    }
}