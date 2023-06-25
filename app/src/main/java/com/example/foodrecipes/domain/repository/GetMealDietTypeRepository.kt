package com.example.foodrecipes.domain.repository

import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface GetMealDietTypeRepository {

    suspend fun getMealDietTypeData():Flow<ResultEvent<MealTypeDietTypeDataModel>>
}