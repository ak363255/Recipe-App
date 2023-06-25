package com.example.foodrecipes.domain.repository

import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.usecase.GetRecipeByQueryUseCase
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface GetRecipeByQueryRepo {
    suspend fun getRecipeByQuery(params:GetRecipeByQueryUseCase.GetRecipeByQueryUseCaseDataParams):Flow<ResultEvent<RecipeDomainModel>>
}