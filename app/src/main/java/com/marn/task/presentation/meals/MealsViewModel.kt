package com.marn.task.presentation.meals

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marn.task.domain.entity.CategoryResponse
import com.marn.task.domain.usecase.GetMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MealsViewModel @Inject constructor(private val getMealsUseCase:GetMeals): ViewModel(){

    private val _categories : MutableStateFlow<CategoryResponse?> = MutableStateFlow(null)
    val categories : StateFlow<CategoryResponse?> = _categories
    fun getMealsCategories(){
        viewModelScope.launch {
            try {
                _categories.value = getMealsUseCase()
            }catch (e:Exception) {
                Timber.e(e.message.toString())
            }
        }
    }
}