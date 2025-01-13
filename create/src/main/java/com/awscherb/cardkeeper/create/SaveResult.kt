package com.awscherb.cardkeeper.create

sealed class SaveResult

data object InvalidTitle : SaveResult()
data object InvalidText : SaveResult()
data object SaveSuccess : SaveResult()
data class Failure(val e: Throwable) : SaveResult()
