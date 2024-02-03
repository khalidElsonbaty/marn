@file:OptIn(ExperimentalSerializationApi::class, ExperimentalSerializationApi::class)

package com.marn.task.di

import android.content.Context
import androidx.multidex.BuildConfig.DEBUG
import com.marn.task.data.remote.ApiService
import com.marn.task.data.repo.MealsRepoImpl
import com.marn.task.domain.repo.MealsRepo
import com.marn.task.domain.usecase.GetMeals
import com.marn.task.utils.AppData
import com.marn.task.utils.ErrorInterceptor
import com.marn.task.utils.HostSelectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideUseCase(mealsRepo: MealsRepo): GetMeals {
        return GetMeals(mealsRepo)
    }
}