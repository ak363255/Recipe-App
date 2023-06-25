package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.FavoriteRecipeRepository
import javax.inject.Inject

class GetFavoriteRecipesByIdUseCase @Inject constructor(
    private val recipeRepository: FavoriteRecipeRepository
) {
    suspend operator fun invoke(id: Int) = recipeRepository.getFavoriteRecipeById(id)
}