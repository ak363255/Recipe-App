package com.example.foodrecipes.data.repository

import com.example.foodrecipes.domain.datasource.localDatasource.MealDietTypeDataSource
import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.foodrecipes.domain.repository.GetMealDietTypeRepository
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ImplGetMealDietTypeRepository(
    private val mealDietTypeDataSource: MealDietTypeDataSource
):GetMealDietTypeRepository{
    override suspend fun getMealDietTypeData(): Flow<ResultEvent<MealTypeDietTypeDataModel>> = mealDietTypeDataSource.getMealDietTypeData().map {
            ResultEvent.OnSuccess(it)
        }
}