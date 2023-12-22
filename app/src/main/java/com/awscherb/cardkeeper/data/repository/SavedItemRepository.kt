package com.awscherb.cardkeeper.data.repository

import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SavedItemRepository @Inject constructor(
    private val scannedCodeService: ScannedCodeService,
    private val pkPassService: PkPassService
) {

    fun listSavedItems(
        query: String? = null,
    ): Flow<List<SavedItem>> {
        return combine(
            scannedCodeService.listAll(query),
            pkPassService.listAll(query)
        ) { codes, passes ->
            (codes + passes)
        }
    }

    suspend fun deleteItem(item: SavedItem) {
        when (item) {
            is PkPassModel -> pkPassService.delete(item)
            is ScannedCodeModel -> scannedCodeService.delete(item)
        }
    }
}