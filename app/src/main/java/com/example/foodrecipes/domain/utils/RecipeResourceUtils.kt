package com.example.foodrecipes.domain.utils

import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object RecipeResourceUtils {
    val MEAL_DIET_TYPE_FILE  = "meal_diet.json"
    private var mealDietTypeDataModel:MealTypeDietTypeDataModel? = null
    init {
        loadMealDietListFromJsonFile()
    }

      private fun loadMealDietListFromJsonFile() {
          if(mealDietTypeDataModel == null){
              CoroutineScope(Dispatchers.IO + Job()).launch {
                  try {
                      val fileName = MEAL_DIET_TYPE_FILE
                      val data : MealTypeDietTypeDataModel? = LocalResourceLoaderUtil.loadJSONFromAsset(fileName,MealTypeDietTypeDataModel::class.java) as MealTypeDietTypeDataModel?
                      mealDietTypeDataModel = data
                  }catch (e:Exception){
                      e.printStackTrace()
                  }
              }
          }
    }

    fun getDefaultMealDietTypeList() = mealDietTypeDataModel
}