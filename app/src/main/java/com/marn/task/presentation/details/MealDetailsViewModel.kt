package com.marn.task.presentation.details

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marn.task.domain.entity.Category
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
class MealDetailsViewModel @Inject constructor(): ViewModel(){
    val _categoryDetails = MutableLiveData<Category>()
    val categoryDetails: LiveData<Category> = _categoryDetails
}