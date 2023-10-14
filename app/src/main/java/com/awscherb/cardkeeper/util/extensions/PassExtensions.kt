package com.awscherb.cardkeeper.util.extensions

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor

fun PkPassModel.getForegroundColor() =
    Color(foregroundColor.parseHexColor())