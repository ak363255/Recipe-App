package com.example.foodrecipes.data.repository

import com.example.foodrecipes.data.datasource.localDataSource.ImplGetMealDietTypeDataSoource
import com.example.foodrecipes.domain.datasource.localDatasource.MealDietTypeDataSource
import com.example.foodrecipes.domain.repository.SaveUserPrefRepo
import com.example.foodrecipes.domain.usecase.SaveUserPrefUseCase

class ImplSaveUserPref(
    private val mealDietTypeDataSoource: MealDietTypeDataSource
):SaveUserPrefRepo {
    override suspend fun saveUserPre(params: SaveUserPrefUseCase.SaveUserPrefDataParams) {
        mealDietTypeDataSoource.saveMealDietType(params)
    }
}