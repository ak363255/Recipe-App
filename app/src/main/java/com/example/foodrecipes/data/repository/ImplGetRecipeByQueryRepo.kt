package com.example.foodrecipes.data.repository

import com.example.recipecore.data.model.toDomainModel
import com.example.foodrecipes.domain.datasource.remoteDatasource.recipeDataSource.GetRecipeDataSource
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.repository.GetRecipeByQueryRepo
import com.example.foodrecipes.domain.usecase.GetRecipeByQueryUseCase
import com.example.foodrecipes.domain.utils.ApiErrorMessage
import com.example.recipecore.domain.utils.UrlConstants
import com.example.network.NetworkResponse
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplGetRecipeByQueryRepo(val remoteDataSource:GetRecipeDataSource):GetRecipeByQueryRepo {
    override suspend fun getRecipeByQuery(params: GetRecipeByQueryUseCase.GetRecipeByQueryUseCaseDataParams): Flow<ResultEvent<RecipeDomainModel>> = flow{
        var url = UrlConstants.BASE_URL
        url = url +"&query=${params.query}&addRecipeInformation=${params.addRecipeInformation}&instructionsRequired=${params.instructionsRequired}"
        val data = remoteDataSource.getRecipes(url)
        when(data){
            is NetworkResponse.ApiError -> {
                emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
            }
            is NetworkResponse.NetworkError -> {
                emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))

            }
            is NetworkResponse.Success -> {
                if(data.body.results.size > 0){
                    emit(ResultEvent.OnSuccess(data.body.toDomainModel()))
                }else{
                    emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
                }
            }
            is NetworkResponse.UnknownError -> {
                emit(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))

            }
        }
    }
}