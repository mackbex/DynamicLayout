package com.musinsa.shopping.util

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.abs


class RecyclerViewGestureListener(val recyclerView: RecyclerView, yBuffer: Int = 10) :
    GestureDetector.SimpleOnGestureListener() {
    private val Y_BUFFER = yBuffer
    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (abs(distanceX) > abs(distanceY)) {
            recyclerView.parent.requestDisallowInterceptTouchEvent(true)

        } else if (abs(distanceY) > Y_BUFFER) {
            recyclerView.parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.onScroll(e1, e2, distanceX, distanceY)
    }

    override fun onDown(e: MotionEvent): Boolean {
        recyclerView.parent.requestDisallowInterceptTouchEvent(true)
        return super.onDown(e)
    }
}