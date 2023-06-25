package com.example.foodrecipes.presentation.adapter

import android.text.TextUtils
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.RecipeItemBinding
import com.example.foodrecipes.domain.utils.CommonAdapter
import com.example.foodrecipes.domain.utils.Utility
import com.example.network.ImageLoaderHelper
import com.example.recipecore.domain.model.FavoriteRecipeDataModel
import com.example.recipecore.domain.model.RecipeDataModel

class FavoriteRecipeFragAdapter(
    val itemClickCallBack:(RecipeDataModel)->Unit,
):CommonAdapter<FavoriteRecipeDataModel>() {
    private var isMutliSelect = false
    private var contextualItemCount = 0
    private var actionMode:ActionMode? = null

    override fun bind(viewbinding: ViewDataBinding, position: Int) {
        val binding  = viewbinding as RecipeItemBinding?
        binding?.let {
            val data = differ.currentList[position]
            setData(it,data,position)
        }
    }
    private fun setData(
        recipeItemBinding: RecipeItemBinding,
        favoriteRecipeDataModel: FavoriteRecipeDataModel,
        position: Int
    ) {
        val data = favoriteRecipeDataModel.recipeDataModel
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
        if(favoriteRecipeDataModel.isContextualModeSelected){
            applyContextualSelection(recipeItemBinding,position)
        }else{
            removeContextualModeSelection(recipeItemBinding)
        }

        recipeItemBinding.root.setOnClickListener {
            if(isMutliSelect){
                favoriteRecipeDataModel.isContextualModeSelected = !favoriteRecipeDataModel.isContextualModeSelected
                if(favoriteRecipeDataModel.isContextualModeSelected){
                    contextualItemCount++
                    applyContextualSelection(recipeItemBinding,position)
                }else{
                    contextualItemCount--;
                    removeContextualModeSelection(recipeItemBinding)
                }
                checkForToremoveContextualMode()
                setContextualModeTitle()

            }else{
                itemClickCallBack.invoke(data)
            }
        }
        recipeItemBinding.root.setOnLongClickListener {
            if(!isMutliSelect){
                favoriteRecipeDataModel.isContextualModeSelected = !favoriteRecipeDataModel.isContextualModeSelected
                if(favoriteRecipeDataModel.isContextualModeSelected){
                    contextualItemCount++;
                    applyContextualSelection(recipeItemBinding,position)

                }else{
                    contextualItemCount--
                    removeContextualModeSelection(recipeItemBinding)
                }
                isMutliSelect = true
                recipeItemBinding.root.startActionMode(contextMenuCallback)
                checkForToremoveContextualMode()
                setContextualModeTitle()
                return@setOnLongClickListener true
            }else{
              return@setOnLongClickListener false
            }
        }

        recipeItemBinding.executePendingBindings()
    }

    private fun checkForToremoveContextualMode() {
        if(contextualItemCount ==0){
            actionMode?.finish()
            isMutliSelect = false
        }
    }

    private fun removeContextualModeSelection(recipeItemBinding: RecipeItemBinding){
        recipeItemBinding.cardView.background = Utility.getCustomGradient(16,"#ffffff")
    }

    private fun applyContextualSelection(recipeItemBinding: RecipeItemBinding, position: Int) {
        recipeItemBinding.cardView.background  = Utility.getCustomGradient(16,"#d7d7d7")
    }
    private fun setContextualModeTitle(){
        actionMode?.title = "$contextualItemCount Item Selected"
    }

    private var contextMenuCallback:ActionMode.Callback  = object :ActionMode.Callback{
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            actionMode = p0
            p0?.menuInflater?.inflate(R.menu.favorite_contexual_menu,p1)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
           return true
        }



        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            if(p1?.itemId == R.id.favorite_icon){
                contextMenuClickedCallback?.onDeleteOptionClicked()
                actionMode?.finish()
                return true
            }
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {
            isMutliSelect = false
            if(contextualItemCount > 0){
                removeAllContextualMode()
            }
        }

    }

    private fun removeAllContextualMode(){
        differ.currentList.forEach {
            it.isContextualModeSelected = false
        }
        notifyDataSetChanged()
    }


    override fun getlayout(position: Int): Int {
        return R.layout.recipe_item
    }

    private var contextMenuClickedCallback:ContextualMenuClickCallback? = null
    public fun setContextualMenuCallback(contextMenuClickedCallback:ContextualMenuClickCallback){
        this.contextMenuClickedCallback = contextMenuClickedCallback
    }

    fun clearContextualMode() {
        actionMode?.finish()
    }

    public interface  ContextualMenuClickCallback{
        fun onDeleteOptionClicked()
    }
}