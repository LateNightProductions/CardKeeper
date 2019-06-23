package com.awscherb.cardkeeper.util.extensions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun <V : View> BottomSheetBehavior<V>.expand() {
    state = BottomSheetBehavior.STATE_EXPANDED
}

fun <V : View> BottomSheetBehavior<V>.collapse() {
    state = BottomSheetBehavior.STATE_COLLAPSED
}