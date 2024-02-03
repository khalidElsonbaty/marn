package com.marn.task.utils

import android.os.Build


object AppData {
    var accessToken: String = ""
    var baseUrlEmpty: String = "https://amazonaws.com/"
    var baseUrl: String = "https://www.themealdb.com/api/json/v1/1/"
    var deviceType: String = "Android"
    var osVersion = Build.VERSION.SDK_INT
    var isConnected = true

}