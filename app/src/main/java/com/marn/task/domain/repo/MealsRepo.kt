package com.marn.task.domain.repo

import com.marn.task.domain.entity.CategoryResponse

interface MealsRepo {
   suspend fun getCategoriesFromRemote() : CategoryResponse
}