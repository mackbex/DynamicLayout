package com.musinsa.shopping.util

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


inline fun <reified T: ViewDataBinding> getDataBinding(parent: ViewGroup, id: Int): T =
    DataBindingUtil.inflate<T>(
        LayoutInflater.from(parent.context), id, parent, false
    )

fun RecyclerView.setScrollSensitivity(yBuffer:Int = 10) {
    val gestureDetector
            = GestureDetector(this.context, RecyclerViewGestureListener(this, yBuffer))
    this.addOnItemTouchListener(
        object: RecyclerView.OnItemTouchListener{
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        }
    )
}