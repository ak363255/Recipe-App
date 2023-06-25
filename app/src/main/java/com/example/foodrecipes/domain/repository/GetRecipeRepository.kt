package com.example.foodrecipes.domain.repository

import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import kotlinx.coroutines.flow.Flow

interface GetRecipeRepository {
    suspend fun getRecipe(params:GetRecipeUseCase.GetRecipeUseCaseDataParams):Flow<ResultEvent<RecipeDomainModel>>
    suspend fun getRecipeJoke():Flow<ResultEvent<RecipeJokeDataModel>>
}