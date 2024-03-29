package com.awscherb.cardkeeper.core

import kotlinx.coroutines.flow.Flow

interface SavedItemService<T : SavedItem> {

    fun listAll(query: String? = null): Flow<List<T>>

    suspend fun delete(item: T)

    fun update(item: T): Flow<Result<T>>
}