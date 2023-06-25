package com.example.recipecore.domain.model

data class MealTypeDietTypeDataModel(
    val mealType:HashMap<String,MealType>,
    val dietType:HashMap<String,DietType>
)