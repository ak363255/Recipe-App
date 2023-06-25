package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.GetRecipeRepository
import javax.inject.Inject

class GetRecipeJokeUseCase @Inject constructor(private val recipeRepository: GetRecipeRepository) {
    suspend operator fun invoke()  = recipeRepository.getRecipeJoke()
}