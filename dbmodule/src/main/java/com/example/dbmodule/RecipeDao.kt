package com.example.dbmodule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_db_model")
    suspend fun getRecipes():List<RecipeDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeItem(recipeDbModel: RecipeDbModel)

    @Query("SELECT * FROM recipe_db_model WHERE :id = id")
    suspend fun getRecipe(id:Int):List<RecipeDbModel>

    @Query("DELETE FROM recipe_db_model WHERE :id = id")
    suspend fun deleteFavRecipeById(id:Int)
}