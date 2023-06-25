package com.example.foodrecipes.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.RecipeInstructionLayoutBinding
import com.example.foodrecipes.presentation.adapter.RecipeInstructionsRvAdapter
import com.example.foodrecipes.presentation.viewmodels.RecipeDetailViewModel

class RecipeInstructionFragment :Fragment( R.layout.recipe_instruction_layout){

    var binding:RecipeInstructionLayoutBinding? = null
    val recipeDetailViewModel:RecipeDetailViewModel by activityViewModels()
    var recipeInstructionsRvAdapter:RecipeInstructionsRvAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RecipeInstructionLayoutBinding.bind(view)
        initUi()
        observeChanges()
    }

    private fun observeChanges() {

    }

    private fun initUi() {
        setAdapter()
    }

    private fun setAdapter() {
        recipeInstructionsRvAdapter = RecipeInstructionsRvAdapter()
        binding?.instructionRv?.apply {
            adapter = recipeInstructionsRvAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
        recipeDetailViewModel.getRecipeDataModel()?.analyzedInstructions?.let {
            if(it.size > 0){
                val stepList = it[0].steps
                stepList?.let {
                    if(it.size > 0){
                        recipeInstructionsRvAdapter?.differ?.submitList(it)
                    }else{
                        showNoStepsUi()
                    }
                }
            }else{
                showNoStepsUi()
            }
        }
    }

    private fun showNoStepsUi() {
        binding?.noStepsUi?.parent?.visibility = View.VISIBLE
        binding?.noStepsUi?.noInternetTv?.text = "No Instruction Found."
    }

    companion object{
        val RECIPE_INSTRUCTION_DATA: String = "recipe_instruction_data"

        fun getInstance(bundle: Bundle):RecipeInstructionFragment{
            val fragment = RecipeInstructionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}