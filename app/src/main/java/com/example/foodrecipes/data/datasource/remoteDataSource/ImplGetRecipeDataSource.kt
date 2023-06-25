package com.example.foodrecipes.data.datasource.remoteDataSource

import com.example.recipecore.data.model.RecipeModelDto
import com.example.foodrecipes.domain.datasource.remoteDatasource.recipeDataSource.GetRecipeDataSource
import com.example.network.NetworkHelper
import com.example.network.NetworkResponse
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import kotlinx.coroutines.flow.Flow

class ImplGetRecipeDataSource :GetRecipeDataSource{
    override suspend fun getRecipes(
        url: String,
        header: Map<String, String>
    ): NetworkResponse<RecipeModelDto,Error> = NetworkHelper.makeGetRequest<RecipeModelDto>(url,header)

    override suspend fun getRecipeJoke(
        url: String,
        header: Map<String, String>
    ): NetworkResponse<RecipeJokeDataModel, Error>  = NetworkHelper.makeGetRequest(url,header)

}