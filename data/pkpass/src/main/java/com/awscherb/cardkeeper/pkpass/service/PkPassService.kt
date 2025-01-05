package com.awscherb.cardkeeper.pkpass.service

import com.awscherb.cardkeeper.core.SavedItemService
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import kotlinx.coroutines.flow.Flow

interface PkPassService : SavedItemService<PkPassModel> {

    fun getPass(passId: String): Flow<PkPassModel>

    fun shouldAutoUpdatePass(passId: String): Flow<Boolean>

    suspend fun setAutoUpdatePass(passId: String, autoUpdate: Boolean)

    suspend fun delete(id: String)
}


