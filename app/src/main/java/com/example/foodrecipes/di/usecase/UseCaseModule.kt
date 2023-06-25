package com.example.foodrecipes.di.usecase

import com.example.foodrecipes.data.datasource.localDataSource.ImplGetMealDietTypeDataSoource
import com.example.foodrecipes.data.datasource.localDataSource.ImplRecipeFavoriteDataSource
import com.example.foodrecipes.data.datasource.remoteDataSource.ImplGetRecipeDataSource
import com.example.foodrecipes.data.repository.*
import com.example.foodrecipes.domain.datasource.localDatasource.MealDietTypeDataSource
import com.example.foodrecipes.domain.datasource.localDatasource.RecipeFavoriteDataSource
import com.example.foodrecipes.domain.datasource.remoteDatasource.recipeDataSource.GetRecipeDataSource
import com.example.foodrecipes.domain.repository.*
import com.example.foodrecipes.domain.usecase.GetMealDietTypeUseCase
import com.example.foodrecipes.domain.usecase.GetRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRecipeRepository(getRecipeDataSource: GetRecipeDataSource):GetRecipeRepository{
        return ImplGetRecipeRepository(getRecipeDataSource)
    }

    @Provides
    @Singleton
    fun provideGetRecipeDataSource():GetRecipeDataSource{
        return ImplGetRecipeDataSource()
    }

    @Provides
    @Singleton
    fun provideMealDietTypeDataSource():MealDietTypeDataSource{
        return ImplGetMealDietTypeDataSoource()
    }

    @Provides
    @Singleton
    fun provideMealDietTypeRepository(mealDietTypeDataSource: MealDietTypeDataSource):GetMealDietTypeRepository{
        return ImplGetMealDietTypeRepository(
            mealDietTypeDataSource
        )
    }

    @Provides
    @Singleton
    fun provideSaveUserRepo(mealDietTypeDataSource: MealDietTypeDataSource):SaveUserPrefRepo{
        return ImplSaveUserPref(mealDietTypeDataSource)
    }

    @Provides
    @Singleton
    fun provideUserPrefRepo(mealDietTypeDataSource: MealDietTypeDataSource):GetUserPrefRepository{
        return ImplGetUserPrefRepo(mealDietTypeDataSource)
    }

    @Provides
    @Singleton
    fun provideGetRecipeByQueryRepo(dataSource: GetRecipeDataSource):GetRecipeByQueryRepo{
        return ImplGetRecipeByQueryRepo(dataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteDataSource():RecipeFavoriteDataSource{
        return ImplRecipeFavoriteDataSource()
    }

    @Provides
    @Singleton
    fun provdeFavoriteRecipeRepository(favoriteDataSource: RecipeFavoriteDataSource):FavoriteRecipeRepository{
        return ImplFavoriteRecipeRepository(favoriteDataSource)
    }
}