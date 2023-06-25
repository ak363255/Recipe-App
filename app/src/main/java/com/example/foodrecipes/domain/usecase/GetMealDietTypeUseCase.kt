package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.GetMealDietTypeRepository
import javax.inject.Inject

class GetMealDietTypeUseCase @Inject constructor(private val mealDietTypeRepository: GetMealDietTypeRepository) {
    suspend operator fun invoke() = mealDietTypeRepository.getMealDietTypeData()
}