package com.example.recipecore.domain.model

data class FavoriteRecipeDataModel(
    var isContextualModeSelected:Boolean,
    val recipeDataModel: RecipeDataModel
)