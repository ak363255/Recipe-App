package com.example.foodrecipes.data.repository

import com.example.foodrecipes.domain.datasource.localDatasource.MealDietTypeDataSource
import com.example.recipecore.domain.model.UserPrefMealAndDietDataModel
import com.example.foodrecipes.domain.repository.GetUserPrefRepository
import kotlinx.coroutines.flow.Flow

class ImplGetUserPrefRepo(
    val localDataSource:MealDietTypeDataSource
):GetUserPrefRepository {
    override suspend fun getUserPrefData(): Flow<UserPrefMealAndDietDataModel>  = localDataSource.getUserPrefMealDiet()
}