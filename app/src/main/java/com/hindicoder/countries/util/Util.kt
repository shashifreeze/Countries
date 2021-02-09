package com.hindicoder.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.RequestOptions
import com.hindicoder.countries.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {

   val options:RequestOptions = RequestOptions()
       .placeholder(progressDrawable)
       .error(R.mipmap.ic_launcher)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}