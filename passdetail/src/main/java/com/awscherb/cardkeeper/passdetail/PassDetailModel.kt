package com.awscherb.cardkeeper.passdetail

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.types.Barcode

data class PassDetailModel(
    val id: String,
    val canBeUpdated: Boolean,
    val type: Type,
    val backgroundColor: Color,
    val backgroundPath: String?,
    val logoPath: String?,
    val description: String,
    val foregroundColor: Color,
    val labelColor: Color,
    val logoText: String?,
    val barcodes: List<Barcode>,
    val isBarcodeSquare: Boolean,
    val footerPath: String?,
    val headerItems: List<FieldConfig>,
    val primaryFields: List<FieldConfig>,
    val auxiliaryFields: List<FieldConfig>,
    val secondaryFields: List<FieldConfig>,
    val transit: TransitModel?,
    val stripPath: String?,
    val thumbnailPath: String?,
    val backItems: List<Pair<String, String>>,
    val webServiceUrl: String?,
    val identifier: String?,
    val authenticationToken: String?
) {

    enum class Type {
        BOARDING_PASS,
        COUPON,
        EVENT_TICKET,
        GENERIC,
        STORE_CARD,
    }
}
