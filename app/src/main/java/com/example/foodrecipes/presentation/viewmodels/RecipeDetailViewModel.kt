package com.example.foodrecipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.domain.usecase.AddFavoriteRecipeUseCase
import com.example.foodrecipes.domain.usecase.GetFavoriteRecipesByIdUseCase
import com.example.foodrecipes.domain.usecase.RemoveFavoriteRecipeUseCase
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDetailDataModel
import com.example.foodrecipes.presentation.adapter.RecipeDetailViewPagerAdapter
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val addFavoriteRecipeUseCase: AddFavoriteRecipeUseCase,
    private val getFavoriteRecipesByIdUseCase: GetFavoriteRecipesByIdUseCase,
    private val removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase
) : ViewModel() {

    private val _recipeDetailViewPagerData: MutableLiveData<ResultEvent<List<RecipeDetailDataModel>>> =
        MutableLiveData()
    val recipeDetailViewPagerData: LiveData<ResultEvent<List<RecipeDetailDataModel>>> get() = _recipeDetailViewPagerData

    private var recipeDataModel: RecipeDataModel? = null

    fun setRecipeDataModel(recipeDataModel: RecipeDataModel) {
        this.recipeDataModel = recipeDataModel
    }

    fun getRecipeDataModel() = recipeDataModel
    fun getRecipeDetailViewPagerData(recipeDomainModel: RecipeDataModel) = viewModelScope.launch {
        val recipeDetailDataList = mutableListOf<RecipeDetailDataModel>()
        recipeDetailDataList.add(
            RecipeDetailDataModel(
                RecipeDetailViewPagerAdapter.RECIPE_OVERVIEW,
                recipeDomainModel = recipeDomainModel,
                "OverView"
            )
        )
        recipeDetailDataList.add(
            RecipeDetailDataModel(
                RecipeDetailViewPagerAdapter.RECIPE_INSTRUCTIONS,
                recipeDomainModel = recipeDomainModel,
                "Instruction"
            )
        )
        _recipeDetailViewPagerData.postValue(ResultEvent.OnSuccess(recipeDetailDataList))
    }

    private val _favoriteRecipe: MutableLiveData<ResultEvent<RecipeDataModel?>> = MutableLiveData()
    val favoriteRecipe: LiveData<ResultEvent<RecipeDataModel?>> get() = _favoriteRecipe
    fun getFavoriteRecipeById(id: Int) = viewModelScope.launch {
        getFavoriteRecipesByIdUseCase(id).collectLatest {
            _favoriteRecipe.postValue(it)
        }
    }


    private val _favoriteRecipeRemoved: MutableLiveData<ResultEvent<Boolean>> = MutableLiveData()
    val favoriteRecipeRemoved: LiveData<ResultEvent<Boolean>> get() = _favoriteRecipeRemoved
    fun removeFavoriteRecipeById(id: Int) = viewModelScope.launch {
        removeFavoriteRecipeUseCase(id).collectLatest {
            _favoriteRecipeRemoved.postValue(it)
        }
    }


    private val _isRecipAdded: MutableLiveData<ResultEvent<Boolean>> = MutableLiveData()
    val isRecipAdded: LiveData<ResultEvent<Boolean>> get() = _isRecipAdded
    fun addToFavoriteRecipe(recipeDataModel: RecipeDataModel) = viewModelScope.launch {
        addFavoriteRecipeUseCase(recipeDataModel).collectLatest {
            _isRecipAdded.postValue(it)
        }
    }




}