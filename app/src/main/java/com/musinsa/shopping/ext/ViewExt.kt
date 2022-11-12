package com.musinsa.shopping.ext

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.musinsa.shopping.R


@BindingAdapter("android:src")
fun ImageView.glide(url:String?) {
    Glide.with(this.context)
        .load(url ?: kotlin.run { ColorDrawable(ContextCompat.getColor(this.context, R.color.grey_04)) })
        .fitCenter()
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.grey_04)))
        .into(this)
}
