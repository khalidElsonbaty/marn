package com.marn.task.utils


import com.marn.task.utils.AppData.baseUrl
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostSelectionInterceptor @Inject constructor() : Interceptor {
    @Volatile
    private var host = baseUrl.toHttpUrlOrNull()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        var newUrl: HttpUrl? = null
        try {
            newUrl = request.url.newBuilder()
                .scheme(host?.scheme.toString())
                .host(host?.toUrl()?.toURI()?.host.toString())
                .build()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        request = request.newBuilder()
            .url(newUrl!!)
            .build()

        return chain.proceed(request)
    }
}