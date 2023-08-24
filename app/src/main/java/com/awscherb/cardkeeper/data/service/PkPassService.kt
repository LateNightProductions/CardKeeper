package com.awscherb.cardkeeper.data.service

import com.awscherb.cardkeeper.data.model.PkPassModel
import kotlinx.coroutines.flow.Flow

interface PkPassService : SavedItemService<PkPassModel> {

    fun getPass(passId: String): Flow<PkPassModel>

}


