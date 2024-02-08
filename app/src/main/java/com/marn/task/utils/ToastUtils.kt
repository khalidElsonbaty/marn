package com.marn.task.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtils {
    private var toast: Toast? = null

    fun Context.showToast(message: CharSequence?) {
        toast?.cancel()
        toast = message?.let { Toast.makeText(this, it, Toast.LENGTH_LONG) }?.apply { show() }
    }

    fun Context.showToast(@StringRes message: Int) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG).apply { show() }
    }
}