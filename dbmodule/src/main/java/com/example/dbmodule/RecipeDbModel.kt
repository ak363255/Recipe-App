package com.example.dbmodule

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_db_model")
data class RecipeDbModel(
    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var recipeData:String
)
