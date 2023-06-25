package com.example.recipecore.data.model

import com.example.recipecore.domain.model.RecipeContent
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel

data class RecipeModelDto(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)


fun RecipeModelDto.toDomainModel():RecipeDomainModel {
    val recipeList = mutableListOf<RecipeDataModel>()
    this.results.forEach{
        val recipeContentList  = mutableListOf<RecipeContent>()
        recipeContentList.add(RecipeContent(name = "Vegan",desired =it.vegan))
        recipeContentList.add(RecipeContent(name = "Vegetarian",desired =it.vegetarian))
        recipeContentList.add(RecipeContent(name = "Cheap",desired =it.cheap))
        recipeContentList.add(RecipeContent(name = "GlutenFree",desired =it.glutenFree))
        recipeContentList.add(RecipeContent(name = "DairyFree",desired =it.dairyFree))
        recipeContentList.add(RecipeContent(name = "VeryHealthy",desired =it.veryHealthy))
        recipeContentList.add(RecipeContent(name = "VeryPopular",desired =it.veryPopular))
        recipeContentList.add(RecipeContent(name = "Sustainable",desired =it.sustainable))
        recipeList.add(
        RecipeDataModel(
            imageUrl = it.image?:"",
            title = it.title?:"",
            recipeDesc = it.summary?:"",
            noOfLike = it.likes,
            duration = it.readyInMinutes,
            veg = it.vegan,
            analyzedInstructions = it.analyzedInstructions,
            recipeContent =  recipeContentList,
            id = it.id
        )
        )
    }
    return RecipeDomainModel(
        recipes = recipeList,
        totalCount =  this.totalResults,
        pageNo = this.offset,
        pageSize = this.number
    )
}
