package com.marn.task.domain.entity


import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val categories: List<Category>?= null
)