package com.example.foodrecipes.presentation.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecore.domain.model.DietType
import com.example.recipecore.domain.model.MealType
import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.foodrecipes.domain.usecase.GetMealDietTypeUseCase
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import com.example.foodrecipes.domain.usecase.SaveUserPrefUseCase
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeFilterViewModel @Inject constructor(
    private val getMealDietTypeUseCase: GetMealDietTypeUseCase,
    private val saveUserPrefUseCase: SaveUserPrefUseCase
):ViewModel() {
    private var mealTypeDietTypeDataModel:MealTypeDietTypeDataModel? = null
    private val _mealDietTypeData:MutableLiveData<ResultEvent<MealTypeDietTypeDataModel>> = MutableLiveData()
    val mealDietTypeData:LiveData<ResultEvent<MealTypeDietTypeDataModel>> get() = _mealDietTypeData

    fun getMealDietType() = viewModelScope.launch {
        getMealDietTypeUseCase().collectLatest {
            _mealDietTypeData.postValue(it)
        }
    }

    fun setMealDietTypeDataModel(mealTypeDietTypeDataModel: MealTypeDietTypeDataModel){
        this.mealTypeDietTypeDataModel = mealTypeDietTypeDataModel
    }
    fun getMealDietTypeDataModel() = mealTypeDietTypeDataModel
    fun saveUserPref(mealType: MealType?, dietType: DietType?) = viewModelScope.launch(Dispatchers.IO + Job()){
        if(mealType != null && dietType != null){
            val params = SaveUserPrefUseCase.SaveUserPrefDataParams(mealType = mealType,dietType = dietType)
            saveUserPrefUseCase(params)
        }
    }
}