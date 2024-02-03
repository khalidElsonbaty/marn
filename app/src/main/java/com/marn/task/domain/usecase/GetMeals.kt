package com.marn.task.domain.usecase

import com.marn.task.domain.repo.MealsRepo

class GetMeals (private val mealsRepo:MealsRepo) {
   suspend operator fun invoke() = mealsRepo.getCategoriesFromRemote()
}