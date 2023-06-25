package com.example.dbmodule.di

import android.content.Context
import androidx.room.Room
import com.example.dbmodule.RecipeRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context):RecipeRoomDb{
        return Room.databaseBuilder(context,RecipeRoomDb::class.java,"RECIPE_DB").build()
    }
}