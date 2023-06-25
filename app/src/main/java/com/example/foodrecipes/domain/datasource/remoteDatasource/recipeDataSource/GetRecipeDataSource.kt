package com.example.foodrecipes.domain.datasource.remoteDatasource.recipeDataSource

import com.example.recipecore.data.model.RecipeModelDto
import com.example.network.NetworkResponse
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import kotlinx.coroutines.flow.Flow

interface GetRecipeDataSource {
    suspend fun getRecipes(url:String,header:Map<String,String> = mutableMapOf()): NetworkResponse<RecipeModelDto, Error>
    suspend fun getRecipeJoke(url:String,header: Map<String, String> = mutableMapOf()) :NetworkResponse<RecipeJokeDataModel,Error>
}