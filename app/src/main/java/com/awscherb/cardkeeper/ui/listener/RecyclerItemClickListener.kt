package com.awscherb.cardkeeper.ui.listener

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListener constructor(
        context: Context,
        private val mListener: OnItemClickListener?)
    : androidx.recyclerview.widget.RecyclerView.OnItemTouchListener {

    private val mGestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent) = true
    })

    //================================================================================
    // Event Handlers
    //================================================================================

    override fun onInterceptTouchEvent(view: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            childView.isPressed = true
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(view: androidx.recyclerview.widget.RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    //================================================================================
    // Click interface
    //================================================================================

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

}