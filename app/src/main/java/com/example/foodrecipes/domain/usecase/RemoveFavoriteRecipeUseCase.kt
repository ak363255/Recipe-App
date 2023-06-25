package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.FavoriteRecipeRepository
import javax.inject.Inject

class RemoveFavoriteRecipeUseCase @Inject constructor(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) {
    suspend operator fun invoke(id:Int) = favoriteRecipeRepository.removeFromFavRecipeById(id)
}