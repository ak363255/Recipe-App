package com.example.foodrecipes.domain.datasource.localDatasource

import com.example.recipecore.domain.model.RecipeDataModel

interface RecipeFavoriteDataSource {
    suspend fun addToFavoriteRecipe(recipeDataModel: RecipeDataModel)
    suspend fun getFavoriteRecipes():List<RecipeDataModel>
    suspend fun getRecipeById(id:Int):RecipeDataModel?
    suspend fun removeFromFavRecipeById(id:Int)
}