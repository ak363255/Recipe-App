package com.example.foodrecipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.domain.usecase.GetRecipeJokeUseCase
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeRecipeViewModel @Inject constructor(
    private val recipeJokeUseCase: GetRecipeJokeUseCase
):ViewModel() {

    private val _recipeJokeData:MutableLiveData<ResultEvent<RecipeJokeDataModel>> = MutableLiveData()
     val recipeJokeData:LiveData<ResultEvent<RecipeJokeDataModel>> get() = _recipeJokeData

    fun getRandomFoodJodke() = viewModelScope.launch {
        recipeJokeUseCase().collectLatest {
            _recipeJokeData.postValue(it)
        }
    }
}