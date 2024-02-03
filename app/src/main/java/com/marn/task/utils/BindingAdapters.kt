package com.marn.task.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.marn.task.R

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, url: String?) {
    Glide.with(imageView).load(url).error(R.drawable.ic_meal_logo).centerCrop()
        .placeholder(R.drawable.ic_meal_logo)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(imageView)
}
