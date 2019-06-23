package com.awscherb.cardkeeper.util.behavior

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.awscherb.cardkeeper.util.extensions.collapse

import com.google.android.material.bottomsheet.BottomSheetBehavior

class AutoCloseBottomSheetBehavior<V : View>(context: Context, attrs: AttributeSet) :
    BottomSheetBehavior<V>(context, attrs) {

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent
    ): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && state == BottomSheetBehavior.STATE_EXPANDED) {

            val outRect = Rect()
            child.getGlobalVisibleRect(outRect)

            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                collapse()
            }
        }
        return super.onInterceptTouchEvent(parent, child, event)
    }
}