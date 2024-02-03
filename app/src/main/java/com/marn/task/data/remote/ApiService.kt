package com.marn.task.data.remote

import com.marn.task.domain.entity.CategoryResponse
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

}