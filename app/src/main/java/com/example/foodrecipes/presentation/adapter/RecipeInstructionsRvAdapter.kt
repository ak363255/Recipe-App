package com.example.foodrecipes.presentation.adapter

import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.foodrecipes.R
import com.example.recipecore.data.model.AnalyzedInstruction
import com.example.recipecore.data.model.Step
import com.example.foodrecipes.databinding.RecipeInstructionItemBinding
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.foodrecipes.domain.utils.CommonAdapter
import com.example.foodrecipes.domain.utils.Utility

class RecipeInstructionsRvAdapter :CommonAdapter<Step>() {
    override fun bind(viewbinding: ViewDataBinding, position: Int) {
        val data = differ.currentList[position]
        val recipeInstructionItemBinding = viewbinding as RecipeInstructionItemBinding
        bindData(data,position,recipeInstructionItemBinding)
    }

    private fun bindData(
        data: Step?,
        position: Int,
        recipeInstructionItemBinding: RecipeInstructionItemBinding
    ) {
        data?.let {step ->
            recipeInstructionItemBinding.stepTv.text = "Step ${step.number}"
            recipeInstructionItemBinding.ingredientTv.text = step.step
            recipeInstructionItemBinding.description.removeAllViews()
            step.ingredients?.forEach {
                val item = LayoutInflater.from(recipeInstructionItemBinding.root.context).inflate(R.layout.meal_type_chip_item,null)
                val titleTv = item.findViewById<TextView>(R.id.chip_title)
                titleTv.text = it.name
                titleTv.setTextColor(ContextCompat.getColor(recipeInstructionItemBinding.root.context,R.color.ads_303030))
                titleTv.background = Utility.getCustomGradient(16,1,"#d7d7d7","#ffffff")
                recipeInstructionItemBinding.description.addView(item)
            }

        }
    }

    override fun getlayout(position: Int): Int {
        return R.layout.recipe_instruction_item
    }
}