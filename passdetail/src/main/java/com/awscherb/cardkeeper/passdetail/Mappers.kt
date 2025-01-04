package com.awscherb.cardkeeper.passdetail

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.pkpass.model.PassInfoType
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.TransitType
import com.awscherb.cardkeeper.pkpass.model.canBeUpdated
import com.awscherb.cardkeeper.pkpass.model.findOriginDestination
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTransitType
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.isBarcodeSquare
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.passInfoType

internal object Mappers {

    fun detailModel(model: PkPassModel): PassDetailModel {
        return PassDetailModel(
            id = model.id,
            backgroundColor = Color(model.backgroundColor.parseHexColor()),
            foregroundColor = Color(model.foregroundColor.parseHexColor()),
            labelColor = Color(model.labelColor.parseHexColor()),
            canBeUpdated = model.canBeUpdated(),
            isBarcodeSquare = model.isBarcodeSquare(),
            webServiceUrl = model.webServiceURL,
            authenticationToken = model.authenticationToken,
            backgroundPath = model.backgroundPath,
            logoPath = model.logoPath,
            logoText = model.logoText,
            type = when (model.passInfoType) {
                PassInfoType.BOARDING_PASS -> PassDetailModel.Type.BOARDING_PASS
                PassInfoType.COUPON -> PassDetailModel.Type.COUPON
                PassInfoType.EVENT_TICKET -> PassDetailModel.Type.EVENT_TICKET
                PassInfoType.STORE_CARD -> PassDetailModel.Type.STORE_CARD
                else -> PassDetailModel.Type.GENERIC
            },
            description = model.description,
            footerPath = model.footerPath,
            stripPath = model.stripPath,
            thumbnailPath = model.thumbnailPath,
            transit = if (model.passInfoType == PassInfoType.BOARDING_PASS) model.findPassInfo()
                ?.findOriginDestination()?.let { (origin, destination) ->
                TransitModel(
                    originLabel = origin.label.orEmpty(),
                    originValue = origin.value.orEmpty(),
                    destinationLabel = destination.label.orEmpty(),
                    destinationValue = destination.value.orEmpty(),
                    type = when (model.findPassInfo()?.getTransitType()) {
                        TransitType.AIR -> TransitModel.Type.AIR
                        TransitType.BOAT -> TransitModel.Type.BOAT
                        TransitType.BUS -> TransitModel.Type.BUS
                        TransitType.TRAIN -> TransitModel.Type.TRAIN
                        else -> TransitModel.Type.GENERIC
                    }
                )
            } else null,
            identifier = model.passTypeIdentifier,
            barcodes = buildList {
                model.barcode?.let { first -> add(first) }
                model.barcodes?.let { codes ->
                    addAll(codes)
                }
            },
            headerItems = buildList {
                model.findPassInfo()?.headerFields?.let {
                    if (it.isNotEmpty()) {
                        val firstPass = it[0]
                        add(
                            FieldConfig(
                                label = model.getTranslatedLabel(firstPass.label),
                                value = model.getTranslatedValue(firstPass.typedValue),
                                labelColor = model.labelColor.parseHexColor(),
                                valueColor = model.foregroundColor.parseHexColor()
                            )
                        )

                        if (it.size > 1) {
                            val secondPass = it[1]

                            add(
                                FieldConfig(
                                    label = model.getTranslatedLabel(secondPass.label),
                                    value = model.getTranslatedValue(secondPass.typedValue),
                                    labelColor = model.labelColor.parseHexColor(),
                                    valueColor = model.foregroundColor.parseHexColor()
                                )
                            )
                        }
                    }
                }
            },
            primaryFields = model.findPassInfo()?.primaryFields?.map { field ->
                FieldConfig(
                    label = model.getTranslatedLabel(field.label),
                    value = model.getTranslatedValue(field.typedValue),
                    labelColor = model.labelColor.parseHexColor(),
                    valueColor = model.foregroundColor.parseHexColor()
                )
            }.orEmpty(),
            auxiliaryFields = model.findPassInfo()?.auxiliaryFields?.map { field ->
                FieldConfig(
                    label = model.getTranslatedLabel(field.label),
                    value = model.getTranslatedValue(field.typedValue),
                    labelColor = model.labelColor.parseHexColor(),
                    valueColor = model.foregroundColor.parseHexColor()
                )
            }.orEmpty(),
            secondaryFields = model.findPassInfo()?.secondaryFields?.map { field ->
                FieldConfig(
                    label = model.getTranslatedLabel(field.label),
                    value = model.getTranslatedValue(field.typedValue),
                    labelColor = model.labelColor.parseHexColor(),
                    valueColor = model.foregroundColor.parseHexColor()
                )
            }.orEmpty(),
            backItems = model.findPassInfo()?.backFields
                ?.filter { it.label != null }
                ?.map { field ->
                    model.getTranslatedLabel(field.label)!! to
                            model.getTranslatedValue(field.typedValue)
                } ?: emptyList()

        )
    }
}