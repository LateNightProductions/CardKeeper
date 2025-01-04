package com.awscherb.cardkeeper.passdetail

data class TransitModel(
    val originLabel: String,
    val originValue: String,
    val destinationLabel: String,
    val destinationValue: String,
    val type: Type
) {
    enum class Type {
        AIR,
        BOAT,
        BUS,
        GENERIC,
        TRAIN
    }
}
