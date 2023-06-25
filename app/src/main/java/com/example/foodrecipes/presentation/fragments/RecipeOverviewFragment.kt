package com.example.foodrecipes.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.RecipeOverviewFragmentBinding
import com.example.foodrecipes.domain.utils.ApiErrorMessage
import com.example.foodrecipes.domain.utils.Utility
import com.example.foodrecipes.domain.utils.Utility.showSnackBar
import com.example.foodrecipes.presentation.viewmodels.RecipeDetailViewModel
import com.example.network.ImageLoaderHelper
import com.example.network.ResultEvent
import com.example.recipecore.domain.model.RecipeDataModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment:Fragment(R.layout.recipe_overview_fragment) {

    private var recipeOverviewFragmentBinding:RecipeOverviewFragmentBinding? = null
    private var isFavoriteRecipe:Boolean = false

    private val recipeDetailViewModel:RecipeDetailViewModel by activityViewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavoriteRecipe()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeOverviewFragmentBinding = RecipeOverviewFragmentBinding.bind(view)
        initUi()
        oberveChanges()
    }

    private fun oberveChanges() {
        recipeDetailViewModel.isRecipAdded.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    onRecipeAddToFavFailed(it.msg)
                }
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    onSuccessOfRecipeAddedToFav(it.data)
                }
            }

        }

        recipeDetailViewModel.favoriteRecipe.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {}
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    onSuccessOfFavRecipe(it.data)
                }
            }
        }

        recipeDetailViewModel.favoriteRecipeRemoved.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    onFailureOfRemoveRecipeFromFav()
                }
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    onRecipeRemoveFromFav()
                }
            }
        }
    }

    private fun onRecipeRemoveFromFav() {
        recipeOverviewFragmentBinding?.root?.let {
            removedFromFavoriteRecipe()
            requireContext().showSnackBar(it,"Recipe Removed From Fav",R.color.white)
        }
    }

    private fun onFailureOfRemoveRecipeFromFav() {
        recipeOverviewFragmentBinding?.root?.let {
            requireContext().showSnackBar(it,ApiErrorMessage.UNKNOWN_ERROR,R.color.ads_d8232a)
        }
    }

    private fun onSuccessOfFavRecipe(data: RecipeDataModel?) {
        if(data == null){
            removedFromFavoriteRecipe()
        }else{
            addedToFavoriteRecipe()
        }
    }

    fun addedToFavoriteRecipe(){
        isFavoriteRecipe = true
        var color = ContextCompat.getColor(requireContext(),R.color.ads_d8232a)
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.ic_fav_fill_icon)
        drawable?.setTint(color)
        recipeOverviewFragmentBinding?.favicon?.setImageDrawable(drawable)
    }
    fun removedFromFavoriteRecipe(){
        isFavoriteRecipe = false
        var color = ContextCompat.getColor(requireContext(),R.color.ads_d7d7d7)
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.ic_fav_fill_icon)
        drawable?.setTint(color)
        recipeOverviewFragmentBinding?.favicon?.setImageDrawable(drawable)
    }

    private fun onSuccessOfRecipeAddedToFav(data: Boolean) {
        recipeOverviewFragmentBinding?.root?.let {view ->
           requireContext().showSnackBar(view,"Recipe Added To Fav",R.color.white)
            addedToFavoriteRecipe()
        }
    }

    private fun onRecipeAddToFavFailed(msg: String) {
        recipeOverviewFragmentBinding?.root?.let {view ->
            requireContext().showSnackBar(view,ApiErrorMessage.UNKNOWN_ERROR,R.color.ads_d8232a)
        }
    }

    private fun initUi(){
        setTitle()
        setImage()
        setDescription()
        setRecipeContent()
        setClickListener()
        isFavoriteRecipe()

    }

    private fun isFavoriteRecipe() {
        recipeDetailViewModel.getRecipeDataModel()?.let {
            recipeDetailViewModel.getFavoriteRecipeById(it.id)
        }
    }

    private fun setClickListener(){
        recipeOverviewFragmentBinding?.favicon?.setOnClickListener {
               if(isFavoriteRecipe){
                   recipeDetailViewModel.getRecipeDataModel()?.let {
                       recipeDetailViewModel.removeFavoriteRecipeById(it.id)
                   }
               }else{
                   recipeDetailViewModel.getRecipeDataModel()?.let {
                       recipeDetailViewModel.addToFavoriteRecipe(it)
                   }
               }
        }
    }

    private fun setRecipeContent() {
        recipeDetailViewModel.getRecipeDataModel()?.recipeContent?.let {contentList ->
            recipeOverviewFragmentBinding?.recipeContentFlowlayout?.let {flowLayout ->
                flowLayout.removeAllViews()
                contentList.forEach {
                    val item = LayoutInflater.from(requireContext()).inflate(R.layout.recipe_overview_content_item,null)
                    val title = item.findViewById<TextView>(R.id.content_title)
                    val imageView = item.findViewById<ImageView>(R.id.tick_image)
                    title.text = it.name?:""
                    val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.circle_bg_d8d8d8)

                    val color = if(it.desired?:false)R.color.ads_32B1A5 else R.color.ads_d7d7d7
                    drawable?.setTint(ContextCompat.getColor(requireContext(),color))
                    imageView.background = drawable
                    flowLayout.addView(item)
                }

            }
        }
    }

    private fun setTitle() {
        recipeDetailViewModel.getRecipeDataModel()?.let {
            it.title?.let {
                recipeOverviewFragmentBinding?.recipeTitle?.text = it
            }
        }
    }

    private fun setDescription() {
        recipeDetailViewModel.getRecipeDataModel()?.let {
            it.recipeDesc?.let {
                recipeOverviewFragmentBinding?.description?.let {textView ->
                    Utility.setHtmlText(textView,it)
                }
            }
        }
    }

    private fun setImage() {
        recipeDetailViewModel.getRecipeDataModel()?.let {
            it.imageUrl?.let {url ->
                recipeOverviewFragmentBinding?.imageView?.let {imageView ->
                    ImageLoaderHelper.loadImage(imageView,url)
                }
            }
        }
    }

    companion object{
        val RECIPE_OVERVIEW_DATA = "recipe_overview_data"
        fun getInstance(bundle:Bundle):RecipeOverviewFragment{
            val fragment = RecipeOverviewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}