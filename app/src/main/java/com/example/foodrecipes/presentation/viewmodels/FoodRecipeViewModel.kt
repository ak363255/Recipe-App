package com.example.foodrecipes.presentation.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.repository.GetRecipeByQueryRepo
import com.example.foodrecipes.domain.usecase.GetRecipeByQueryUseCase
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import com.example.foodrecipes.domain.usecase.GetUserPrefMealAndDietTypeUseCase
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipeViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val getUserPrefMealDietTypeUseCase: GetUserPrefMealAndDietTypeUseCase,
    private val getRecipeByQueryUseCase: GetRecipeByQueryUseCase
):ViewModel() {

    private val _recipeData:MutableLiveData<ResultEvent<RecipeDomainModel>> = MutableLiveData()
     val recipeData:LiveData<ResultEvent<RecipeDomainModel>> get() = _recipeData

    fun getRecipes() = viewModelScope.launch {
        getUserPrefMealDietTypeUseCase().collectLatest {
            if(it.dietType != null && it.mealType != null){
                val params = GetRecipeUseCase.GetRecipeUseCaseDataParams(
                    type =  it.mealType!!.title,
                    diet = it.dietType!!.title
                )
               getRecipes(params)
            }else{
                val params = GetRecipeUseCase.GetRecipeUseCaseDataParams()
                getRecipes(params)
            }
        }
    }
    fun getRecipes(params:GetRecipeUseCase.GetRecipeUseCaseDataParams) = viewModelScope.launch {
        getRecipeUseCase(params).collectLatest {
            _recipeData.postValue(it)
        }
    }

    fun applyFilter(mealType: String?, dietType: String?)  = viewModelScope.launch{
        if(!TextUtils.isEmpty(mealType) && !TextUtils.isEmpty(dietType)){
            val getRecipeUseCaseParams = GetRecipeUseCase.GetRecipeUseCaseDataParams(
                type = mealType!!,
                diet = dietType!!
            )
            getRecipes(params = getRecipeUseCaseParams)
        }
    }

    fun getRecipByQuery(params:GetRecipeByQueryUseCase.GetRecipeByQueryUseCaseDataParams) = viewModelScope.launch {
          getRecipeByQueryUseCase(params).collectLatest {
              _recipeData.postValue(it)
          }
    }
}