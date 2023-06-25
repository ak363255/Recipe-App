package com.example.foodrecipes.presentation.adapter

import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.RecipeItemBinding
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.foodrecipes.domain.utils.CommonAdapter
import com.example.foodrecipes.domain.utils.Utility
import com.example.network.ImageLoaderHelper

class RecipeFragAdapter(
    val itemClickCallBack:(RecipeDataModel)->Unit
):CommonAdapter<RecipeDataModel>() {
    override fun bind(viewbinding: ViewDataBinding, position: Int) {
        val binding  = viewbinding as RecipeItemBinding?
        binding?.let {
            val data = differ.currentList[position]
            setData(it,data,position)
        }
    }
    private fun setData(
        recipeItemBinding: RecipeItemBinding,
        data: RecipeDataModel,
        position: Int
    ) {
        Utility.setHtmlText(recipeItemBinding.recipeDesc,data.recipeDesc)
        recipeItemBinding.recipeTitle.text = data.title
        val drawable1 = ContextCompat.getDrawable(recipeItemBinding.root.context,R.drawable.ic_fav_fill_icon)
        drawable1?.setTint(ContextCompat.getColor(recipeItemBinding.root.context,R.color.ads_d8232a))
        recipeItemBinding.likeView.icon.setImageDrawable(drawable1)
        recipeItemBinding.likeView.iconDesc.text = data.noOfLike.toString()
        recipeItemBinding.likeView.iconDesc.setTextColor(ContextCompat.getColor(recipeItemBinding.root.context,R.color.ads_d8232a))



        val drawable2 = ContextCompat.getDrawable(recipeItemBinding.root.context,R.drawable.ic_time_icon)
        drawable2?.setTint(ContextCompat.getColor(recipeItemBinding.root.context,R.color.ads_FF9800))
        recipeItemBinding.timeView.icon.setImageDrawable(drawable2)
        recipeItemBinding.timeView.iconDesc.text = data.duration.toString()
        recipeItemBinding.timeView.iconDesc.setTextColor(ContextCompat.getColor(recipeItemBinding.root.context,R.color.ads_FF9800))



        val drawable3 = ContextCompat.getDrawable(recipeItemBinding.root.context,R.drawable.ic_veg)
        val color = if(data.veg)R.color.ads_39A53D else R.color.ads_E7E7E7
        drawable3?.setTint(ContextCompat.getColor(recipeItemBinding.root.context,color))
        recipeItemBinding.vegView.icon.setImageDrawable(drawable3)
        recipeItemBinding.vegView.iconDesc.text = if(data.veg)"Vegan" else "Non-Vegan"
        recipeItemBinding.vegView.iconDesc.setTextColor(ContextCompat.getColor(recipeItemBinding.root.context,if(data.veg)R.color.ads_39A53D else R.color.ads_E7E7E7))

        if(!TextUtils.isEmpty(data.imageUrl)){
            ImageLoaderHelper.loadImage(recipeItemBinding.recipeImage,data.imageUrl)
        }

        recipeItemBinding.root.setOnClickListener {
           itemClickCallBack.invoke(data)
        }

        recipeItemBinding.executePendingBindings()
    }

    override fun getlayout(position: Int): Int {
        return R.layout.recipe_item
    }
}