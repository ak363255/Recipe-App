package com.example.dbmodule

import com.example.network.NetworkHelper
import com.example.recipecore.domain.model.RecipeDataModel

object RecipeDbHelper {
     lateinit var  recipeDb:RecipeRoomDb

    suspend fun insertRecipeData(recipeDataModel: RecipeDataModel){
          val jsonString = NetworkHelper.convertObjectToString(recipeDataModel)
          jsonString?.let {recipeData ->
              val recipeRecipeDataModel = RecipeDbModel(recipeData = recipeData, id = recipeDataModel.id)
              if(this::recipeDb.isInitialized){
                  recipeDb.getRecipeDao().insertRecipeItem(recipeRecipeDataModel)
              }
          }
    }

    suspend fun getAllRecipes():List<RecipeDataModel>{
        val recipeDataList = mutableListOf<RecipeDataModel>()
        if(this::recipeDb.isInitialized){
            val recipeDbDataModel = recipeDb.getRecipeDao().getRecipes()
            recipeDbDataModel.forEach {
                val recipeDataModel = NetworkHelper.convert<RecipeDataModel?>(it.recipeData)
                recipeDataModel?.let {
                    recipeDataList.add(it)
                }
            }
        }
        return recipeDataList
    }

    suspend fun getRecipeById(id:Int):RecipeDataModel?{
        var recipeDataModel:RecipeDataModel? = null
        if(this::recipeDb.isInitialized){
            val recipeDbData = recipeDb.getRecipeDao().getRecipe(id)
            recipeDbData.let {
                it.forEach {
                     recipeDataModel = NetworkHelper.convert(it.recipeData)
                }
            }
        }
       return recipeDataModel
    }

    suspend fun removeFromFavRecipeById(id:Int){
        if(this::recipeDb.isInitialized){
            recipeDb.getRecipeDao().deleteFavRecipeById(id)
        }
    }
}