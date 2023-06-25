package com.example.foodrecipes.data.datasource.localDataSource

import com.example.dbmodule.RecipeDbHelper
import com.example.foodrecipes.domain.datasource.localDatasource.RecipeFavoriteDataSource
import com.example.recipecore.domain.model.RecipeDataModel

class ImplRecipeFavoriteDataSource :RecipeFavoriteDataSource{
    override suspend fun addToFavoriteRecipe(recipeDataModel: RecipeDataModel) {
        return RecipeDbHelper.insertRecipeData(recipeDataModel)
    }

    override suspend fun getFavoriteRecipes(): List<RecipeDataModel> {
        return RecipeDbHelper.getAllRecipes()
    }

    override suspend fun getRecipeById(id: Int): RecipeDataModel? {
        return RecipeDbHelper.getRecipeById(id)
    }

    override suspend fun removeFromFavRecipeById(id: Int) {
         RecipeDbHelper.removeFromFavRecipeById(id)
    }
}