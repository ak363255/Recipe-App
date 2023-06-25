package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.FavoriteRecipeRepository
import com.example.recipecore.domain.model.RecipeDataModel
import javax.inject.Inject

class AddFavoriteRecipeUseCase @Inject constructor(
    private val recipeRepository: FavoriteRecipeRepository
) {
    suspend operator fun invoke(recipeDataModel: RecipeDataModel) =
        recipeRepository.addFavoriteRecipe(recipeDataModel)
}