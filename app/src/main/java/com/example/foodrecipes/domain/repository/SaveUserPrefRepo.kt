package com.example.foodrecipes.domain.repository

import com.example.foodrecipes.domain.usecase.SaveUserPrefUseCase

interface SaveUserPrefRepo {

    suspend fun saveUserPre(params:SaveUserPrefUseCase.SaveUserPrefDataParams)
}