package com.marn.task.data.repo

import com.marn.task.data.remote.ApiService
import com.marn.task.domain.entity.CategoryResponse
import com.marn.task.domain.repo.MealsRepo

class MealsRepoImpl(private val apiService: ApiService) : MealsRepo {
    override suspend fun getCategoriesFromRemote(): CategoryResponse = apiService.getCategories()
}