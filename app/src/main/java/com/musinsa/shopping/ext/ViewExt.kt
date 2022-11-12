package com.musinsa.shopping.ext

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.musinsa.shopping.R
import com.musinsa.shopping.util.toPx


@BindingAdapter("android:src")
fun ImageView.glide(url:String?) {
    Glide.with(this.context)
        .load(url ?: kotlin.run { ColorDrawable(ContextCompat.getColor(this.context, R.color.grey_04)) })
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.grey_04)))
        .into(this)
}
