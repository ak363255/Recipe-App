package com.example.foodrecipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.domain.usecase.AddFavoriteRecipeUseCase
import com.example.foodrecipes.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import com.example.foodrecipes.domain.usecase.RemoveFavoriteRecipeUseCase
import com.example.foodrecipes.domain.utils.ApiErrorMessage
import com.example.network.ResultEvent
import com.example.recipecore.domain.model.FavoriteRecipeDataModel
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    private val getFavoriteRecipeUseCase: GetFavoriteRecipesUseCase,
    private val removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase
):ViewModel() {

    private val _recipeData: MutableLiveData<ResultEvent<List<FavoriteRecipeDataModel>>> = MutableLiveData()
    val recipeData: LiveData<ResultEvent<List<FavoriteRecipeDataModel>>> get() = _recipeData

    fun getRecipes() = viewModelScope.launch {
        getFavoriteRecipeUseCase().collectLatest {
            when(it){
                is ResultEvent.OnFailure -> {
                    _recipeData.postValue(ResultEvent.OnFailure(ApiErrorMessage.UNKNOWN_ERROR))
                }
                ResultEvent.OnLoading -> {
                    _recipeData.postValue(ResultEvent.OnLoading)

                }
                is ResultEvent.OnSuccess -> {
                    val favRecipeList = mutableListOf<FavoriteRecipeDataModel>()
                    it.data.forEach {
                        favRecipeList.add(FavoriteRecipeDataModel(isContextualModeSelected = false , recipeDataModel = it))
                    }
                    _recipeData.postValue(ResultEvent.OnSuccess(favRecipeList))
                }
            }
        }
    }

    fun removeFavRecipeById(id:Int) = viewModelScope.launch {
         removeFavoriteRecipeUseCase(id).collectLatest {

         }
    }
}