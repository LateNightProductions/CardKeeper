package com.awscherb.cardkeeper.items.util

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.passUi.PassHeaderModel
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor

internal object Mappers {
    fun scannedCodeItemModel(item: ScannedCodeModel) =
        with(item) {
            ScannedCodeItemModel(
                id = id.toString(),
                title = title,
                message = text,
                created = created,
                barcodeFormat = format,
                parsedType = parsedType
            )
        }

    fun passItemModel(item: PkPassModel) = with(item) {

        PassItemModel(
            id = id,
            backgroundColor = Color(backgroundColor.parseHexColor()),
            created = created,
            backgroundPath = backgroundPath,
            isEvent = eventTicket != null,
            header = PassHeaderModel(
                logo = logoPath,
                description = description,
                foregroundColor = Color(foregroundColor.parseHexColor()),
                labelColor = Color(labelColor.parseHexColor()),
                logoText = logoText,
                headerConfig = buildList {
                    findPassInfo()?.headerFields?.let {
                        if (it.isNotEmpty()) {
                            val firstPass = it[0]
                            add(
                                FieldConfig(
                                    label = getTranslatedLabel(firstPass.label),
                                    value = getTranslatedValue(firstPass.typedValue),
                                    labelColor = Color(labelColor.parseHexColor()),
                                    valueColor = Color(foregroundColor.parseHexColor())
                                )
                            )

                            if (it.size > 1) {
                                val secondPass = it[1]

                                add(
                                    FieldConfig(
                                        label = getTranslatedLabel(secondPass.label),
                                        value = getTranslatedValue(secondPass.typedValue),
                                        labelColor = Color(labelColor.parseHexColor()),
                                        valueColor = Color(foregroundColor.parseHexColor())
                                    )
                                )
                            }
                        }
                    }
                }
            )
        )
    }

}