package com.awscherb.cardkeeper.util.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

fun <T> Flow<List<T>>.filterOne(): Flow<T> =
    filter { it.isNotEmpty() }
        .map { it[0] }
