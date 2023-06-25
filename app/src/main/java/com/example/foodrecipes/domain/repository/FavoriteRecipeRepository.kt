package com.example.foodrecipes.domain.repository

import com.example.network.ResultEvent
import com.example.recipecore.domain.model.RecipeDataModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipeRepository {
    suspend fun getFavoriteRecipes():Flow<ResultEvent<List<RecipeDataModel>>>
    suspend fun addFavoriteRecipe(recipeDataModel: RecipeDataModel):Flow<ResultEvent<Boolean>>
    suspend fun getFavoriteRecipeById(id:Int):Flow<ResultEvent<RecipeDataModel?>>
    suspend fun removeFromFavRecipeById(id:Int):Flow<ResultEvent<Boolean>>
}