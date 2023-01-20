package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.entity.BarcodeStruct
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.toBarcodeFormat
import com.awscherb.cardkeeper.data.service.PkPassService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PkPassHandler @Inject constructor(
    private val dao: PkPassDao
) : PkPassService {

    override fun listAll(query: String?): Flow<List<PkPassModel>> {
        // return if (query == null) dao.listPkPasses() else dao.listPkPasses(query)
        return flowOf(
            listOf(
                PkPassEntity(
                    description = "JetBlue Boarding Pass",
                    organizationName = "JetBlue",
                    barcode = BarcodeStruct(
                        format = "PKBarcodeFormatAztec".toBarcodeFormat(),
                        message = "M1SCHERB\\/ALEX WALTER MEFLGTTC BOSJFKB6 0317 035P007B0033 147>3180 M6035BB6      " +
                            "        29279          0 B6                        ^160MEYCIQDKPx87Xc+WRhdEFF7CvR8C+QZMETJYZqzw3nGq7NcGmAIhAJQhhgPu6SppTD1bKy4f7praUsQIY0XskT7KArur\\/QfO",
                        messageEncoding = "utf-8",
                        altText = null
                    ),
                    backgroundColor = "rgb(0,41,97)",
                    foregroundColor = "rgb(255,255,255)",
                    labelColor = "rgb(255,255,255)",
                    created = System.currentTimeMillis(),
                    passTypeIdentifier = "pass.com.jetblue.boardingpass",
                    serialNumber = "39229147"
                )
            )
        )
    }

    override suspend fun delete(item: PkPassModel) {
        dao.delete(item.id)
    }
}