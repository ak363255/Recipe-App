package com.example.foodrecipes.domain.datasource.localDatasource

import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.recipecore.domain.model.UserPrefMealAndDietDataModel
import com.example.foodrecipes.domain.usecase.SaveUserPrefUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.internal.ChannelFlow

interface MealDietTypeDataSource {
    suspend fun getMealDietTypeData():Flow<MealTypeDietTypeDataModel>

    suspend fun saveMealDietType(param:SaveUserPrefUseCase.SaveUserPrefDataParams)

    suspend fun getUserPrefMealDiet():Flow<UserPrefMealAndDietDataModel>

}