package com.example.foodrecipes.domain.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodrecipes.RecipeApplication
import com.example.recipecore.domain.model.DietType
import com.example.recipecore.domain.model.MealType
import com.example.network.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

object RecipeDataStorePreference {

    private val RECIPE_DATASTORE = "RECIPE_DATASTORE"
    private val Context.recipeDataStore :DataStore<Preferences> by preferencesDataStore(RECIPE_DATASTORE)

    private object RECIPE_DATASTORE_KEY{
        val mealType = stringPreferencesKey("mealType")
        val dietType = stringPreferencesKey("dietType")
    }

    suspend fun getUserPrefMealType():Flow<MealType?> = RecipeApplication.getApplicationContext().recipeDataStore.data.map {
        val mealTypeString = it[RECIPE_DATASTORE_KEY.mealType]
        if(mealTypeString != null){
            NetworkHelper.convert(mealTypeString)
        }else{
            null
        }
    }

    suspend fun getUserPrefDietType():Flow<DietType?> = RecipeApplication.getApplicationContext().recipeDataStore.data.map {
        val dietTyeString = it[RECIPE_DATASTORE_KEY.dietType]
        if(dietTyeString != null){
            NetworkHelper.convert(dietTyeString)
        }else null
    }

    suspend fun saveUserPrefMealType(userPrefMealDietType:MealType) = RecipeApplication.getApplicationContext().recipeDataStore.edit {
             val mealTypeString = NetworkHelper.convertObjectToString(userPrefMealDietType)
        if(mealTypeString != null){
            it[RECIPE_DATASTORE_KEY.mealType] = mealTypeString
        }
    }


    suspend fun saveUserPrefDietyType(userPrefMealDietType:DietType) = RecipeApplication.getApplicationContext().recipeDataStore.edit {
        val dietTypeString = NetworkHelper.convertObjectToString(userPrefMealDietType)
        if(dietTypeString != null){
            it[RECIPE_DATASTORE_KEY.dietType] = dietTypeString
        }
    }



}
