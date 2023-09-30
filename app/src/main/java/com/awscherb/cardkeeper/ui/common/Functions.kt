package com.awscherb.cardkeeper.ui.common

import androidx.compose.ui.Alignment

fun getAlignmentForFieldText(index: Int, size: Int): Alignment.Horizontal {
    return if (index == size - 1 && size > 1) Alignment.End else Alignment.Start
}