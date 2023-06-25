package com.example.foodrecipes.data.repository

import com.example.recipecore.data.model.toDomainModel
import com.example.foodrecipes.domain.datasource.remoteDatasource.recipeDataSource.GetRecipeDataSource
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.repository.GetRecipeRepository
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import com.example.foodrecipes.domain.utils.ApiErrorMessage
import com.example.recipecore.domain.utils.UrlConstants
import com.example.network.NetworkResponse
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplGetRecipeRepository(private val recipeDataSource:GetRecipeDataSource):GetRecipeRepository {
    override suspend fun getRecipe(params: GetRecipeUseCase.GetRecipeUseCaseDataParams): Flow<ResultEvent<RecipeDomainModel>> = flow {
        emit(ResultEvent.OnLoading)
        var url = UrlConstants.BASE_URL
        url = url +"&type=${params.type}&addRecipeInformation=${params.addRecipeInformation}&diet=${params.diet}&instructionsRequired=${params.instructionsRequired}"
        val data = recipeDataSource.getRecipes(url)
        when(data){
            is NetworkResponse.Success -> {
                emit(ResultEvent.OnSuccess(data.body.toDomainModel()))
            }
            else ->{
                  emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
            }
        }
    }

    override suspend fun getRecipeJoke(): Flow<ResultEvent<RecipeJokeDataModel>>  = flow{
        emit(ResultEvent.OnLoading)
        val url = UrlConstants.GET_RANDOM_RECIPE_JOKE
        val data = recipeDataSource.getRecipeJoke(url)
        when(data){
            is NetworkResponse.Success -> {
                if(data.body.text != null && data.body.text!!.length > 0){
                    emit(ResultEvent.OnSuccess(data.body))
                }else{
                    emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
                }
            }
            else ->{
                emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
            }
        }
    }
}