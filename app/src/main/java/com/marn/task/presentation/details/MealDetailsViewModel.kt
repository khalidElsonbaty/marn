package com.marn.task.presentation.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marn.task.domain.entity.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(): ViewModel(){
    val _categoryDetails = MutableLiveData<Category>()
    val categoryDetails: LiveData<Category> = _categoryDetails
}