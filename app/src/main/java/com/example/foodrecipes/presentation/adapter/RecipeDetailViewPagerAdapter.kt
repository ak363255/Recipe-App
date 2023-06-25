package com.example.foodrecipes.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipecore.domain.model.RecipeDetailDataModel
import com.example.foodrecipes.presentation.fragments.RecipeIngredientFragment
import com.example.foodrecipes.presentation.fragments.RecipeInstructionFragment
import com.example.foodrecipes.presentation.fragments.RecipeOverviewFragment

class RecipeDetailViewPagerAdapter(fragmentManager: FragmentManager,lifecycleOwner: Lifecycle):FragmentStateAdapter(
    fragmentManager,lifecycleOwner
) {
    private var list:MutableList<RecipeDetailDataModel> = mutableListOf()
    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment {
             val data = list[position]
        return when(data.viewType){
            RECIPE_OVERVIEW ->{
                val bundle = Bundle()
                return  RecipeOverviewFragment.getInstance(bundle)

            }
            RECIPE_INSTRUCTIONS ->{
                val bundle = Bundle()
                return  RecipeInstructionFragment.getInstance(bundle)
            }
            else ->{
                val bundle = Bundle()
                return  RecipeOverviewFragment.getInstance(bundle)
            }
        }
    }

    fun updateList(data:List<RecipeDetailDataModel>){
        this.list = data.toMutableList()
        notifyDataSetChanged()

    }

    fun getList() = list
    companion object{
        const val RECIPE_OVERVIEW = 1
        const val RECIPE_INSTRUCTIONS = 2
    }

}