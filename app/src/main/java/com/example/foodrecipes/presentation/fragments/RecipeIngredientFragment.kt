package com.example.foodrecipes.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.foodrecipes.R

class RecipeIngredientFragment :Fragment(R.layout.recipe_ingredient_layout){


    companion object{
        val RECIPE_INGREDIENT_DATA: String = "recipe_ingredient_data"

        fun getInstance(bundle: Bundle):RecipeIngredientFragment{
            val fragment = RecipeIngredientFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}