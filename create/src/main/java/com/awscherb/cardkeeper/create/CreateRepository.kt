package com.awscherb.cardkeeper.create

import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import com.awscherb.cardkeeper.types.ParsedResultType
import javax.inject.Inject
import kotlin.random.Random

class CreateRepository @Inject constructor(
    private val scannedCodeService: ScannedCodeService
) {

    suspend fun create(createModel: CreateModel): Result<Unit> {
        val scannedCode = ScannedCodeEntity(
            title = createModel.title,
            text = createModel.text,
            format = createModel.format,
            created = System.currentTimeMillis(),
            id = Random.nextInt(),
            parsedType = ParsedResultType.TEXT
        )

        return try {
            scannedCodeService.addScannedCode(scannedCode)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}