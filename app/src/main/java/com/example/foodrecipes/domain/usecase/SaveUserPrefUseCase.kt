package com.example.foodrecipes.domain.usecase

import com.example.recipecore.domain.model.DietType
import com.example.recipecore.domain.model.MealType
import com.example.foodrecipes.domain.repository.SaveUserPrefRepo
import javax.inject.Inject

class SaveUserPrefUseCase @Inject constructor(
    private val saveUserPrefRepo: SaveUserPrefRepo
) {

    suspend operator fun invoke(params: SaveUserPrefDataParams) = saveUserPrefRepo.saveUserPre(params)

    data class SaveUserPrefDataParams(
        val mealType: MealType,
        val dietType: DietType
    )
}