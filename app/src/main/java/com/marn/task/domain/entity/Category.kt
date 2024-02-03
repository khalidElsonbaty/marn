package com.marn.task.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val idCategory: String?="", // 1
    val strCategory: String?="", // Beef
    val strCategoryDescription: String?="", // Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]
    val strCategoryThumb: String?="" // https://www.themealdb.com/images/category/beef.png
)
