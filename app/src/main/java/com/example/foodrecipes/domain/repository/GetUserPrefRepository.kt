package com.example.foodrecipes.domain.repository

import com.example.recipecore.domain.model.UserPrefMealAndDietDataModel
import kotlinx.coroutines.flow.Flow

interface GetUserPrefRepository {

    suspend fun getUserPrefData(): Flow<UserPrefMealAndDietDataModel>
}