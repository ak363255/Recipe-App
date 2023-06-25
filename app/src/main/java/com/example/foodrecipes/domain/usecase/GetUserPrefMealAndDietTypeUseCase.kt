package com.example.foodrecipes.domain.usecase

import com.example.foodrecipes.domain.repository.GetUserPrefRepository
import javax.inject.Inject

class GetUserPrefMealAndDietTypeUseCase @Inject constructor(
    private val getUserPrefRepository: GetUserPrefRepository
) {
    suspend operator fun invoke() = getUserPrefRepository.getUserPrefData()
}