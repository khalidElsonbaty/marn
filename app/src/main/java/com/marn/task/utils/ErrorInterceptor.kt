package com.marn.task.utils

import android.content.Context
import android.content.Intent
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.networkResponse?.code == 401) {
            val intent = Intent(AppConstants.AUTH_FAILED_ACTION)
            context.sendBroadcast(intent)
        }
        return response
    }

}