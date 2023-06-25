package com.example.foodrecipes

import android.app.Application
import com.example.dbmodule.RecipeDbHelper
import com.example.dbmodule.RecipeRoomDb
import com.example.foodrecipes.domain.utils.RecipeResourceUtils
import com.example.network.NetworkHelper
import com.example.network.RetrofitClient
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class RecipeApplication:Application() {


    @Inject
    lateinit var retrofitClient: RetrofitClient

    @Inject
    lateinit var recipeRoomDb: RecipeRoomDb

    override fun onCreate() {
        super.onCreate()
        applicationContext_ = this
        initializeRetrofit()
        intializeResource()
        initReicpeRoomDb()
    }

    private fun initReicpeRoomDb() {
        RecipeDbHelper.recipeDb = recipeRoomDb
    }

    private fun intializeResource() {
        RecipeResourceUtils.getDefaultMealDietTypeList()
    }

    private fun initializeRetrofit() {
        NetworkHelper.retrofitClient = retrofitClient
    }

    companion object{
        private lateinit var applicationContext_:RecipeApplication
        fun getApplicationContext() = applicationContext_
    }
}