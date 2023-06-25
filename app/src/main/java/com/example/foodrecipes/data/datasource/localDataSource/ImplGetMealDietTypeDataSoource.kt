package com.example.foodrecipes.data.datasource.localDataSource
import com.example.foodrecipes.domain.datasource.localDatasource.MealDietTypeDataSource
import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.recipecore.domain.model.UserPrefMealAndDietDataModel
import com.example.foodrecipes.domain.usecase.SaveUserPrefUseCase
import com.example.foodrecipes.domain.utils.RecipeDataStorePreference
import com.example.foodrecipes.domain.utils.RecipeResourceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext

class ImplGetMealDietTypeDataSoource : MealDietTypeDataSource{
    override suspend fun getMealDietTypeData(): Flow<MealTypeDietTypeDataModel> {
        val mealTypeFlow = RecipeDataStorePreference.getUserPrefMealType()
        val dietTypeFlow = RecipeDataStorePreference.getUserPrefDietType()
        val result = mealTypeFlow.zip(dietTypeFlow){mealType, dietType ->
            Pair(mealType,dietType)
        }.map {
            val mealDietType = getDeaultMealDietType()
            if(it.first != null && it.second != null){
                if(mealDietType.mealType.contains(it.first!!.id)){
                    mealDietType.mealType[it.first!!.id]?.isSelected = true
                }
                if(mealDietType.dietType.containsKey(it.second!!.id)){
                    mealDietType.dietType[it.second!!.id]?.isSelected = true
                }
            }
            mealDietType
        }
        return result
    }

    override suspend fun saveMealDietType(param: SaveUserPrefUseCase.SaveUserPrefDataParams): Unit = withContext(Dispatchers.IO + Job()){
        RecipeDataStorePreference.saveUserPrefDietyType(param.dietType)
        RecipeDataStorePreference.saveUserPrefMealType(param.mealType)
    }

    override suspend fun getUserPrefMealDiet(): Flow<UserPrefMealAndDietDataModel> {
        val mealTypeFlow = RecipeDataStorePreference.getUserPrefMealType()
        val dietTypeFlow = RecipeDataStorePreference.getUserPrefDietType()
       val result = mealTypeFlow.zip(dietTypeFlow){mealType, dietType ->
          UserPrefMealAndDietDataModel(mealType,dietType)
        }
        return result
    }

    private suspend fun getDeaultMealDietType():MealTypeDietTypeDataModel{
        val data =  RecipeResourceUtils.getDefaultMealDietTypeList()
       return if(data == null){
            MealTypeDietTypeDataModel(hashMapOf(), hashMapOf())
        }else{
            data
        }
    }
}