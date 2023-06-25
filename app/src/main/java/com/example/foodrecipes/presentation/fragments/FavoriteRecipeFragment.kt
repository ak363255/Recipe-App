package com.example.foodrecipes.presentation.fragments

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFavoriteRecipeBinding
import com.example.foodrecipes.presentation.activities.RecipeDetailActivity
import com.example.foodrecipes.presentation.adapter.FavoriteRecipeFragAdapter
import com.example.foodrecipes.presentation.viewmodels.FavoriteRecipeViewModel
import com.example.foodrecipes.presentation.widget.LoaderWidget
import com.example.network.ResultEvent
import com.example.recipecore.domain.model.FavoriteRecipeDataModel
import com.example.recipecore.domain.model.RecipeDataModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipeFragment : Fragment(R.layout.fragment_favorite_recipe) {
    var binding:FragmentFavoriteRecipeBinding? = null
    private var loaderView:LoaderWidget? = null
    private var fragRecipeAdapter: FavoriteRecipeFragAdapter? = null

    private val recipeFavViewModel:FavoriteRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeFavViewModel.getRecipes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeFavViewModel.getRecipes()
        binding = FragmentFavoriteRecipeBinding.bind(view)
        initUi()
        observeChanges()

    }

    private fun initUi() {
         setRecipeRecyclerView()
    }


    private fun showNoFavRecipeUi() {
        binding?.noInternetConnectionUi?.noInternetTv?.text = "No Favorite Recipes"
        binding?.noInternetConnectionUi?.parent?.visibility = View.VISIBLE
    }
    private fun hideNoFavRecipeUi(){
        binding?.noInternetConnectionUi?.parent?.visibility = View.GONE

    }


    private fun observeChanges(){
        recipeFavViewModel.recipeData.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    showNoFavRecipeUi()
                    hideLoader()
                }
                ResultEvent.OnLoading ->{
                    showLoader()
                }
                is ResultEvent.OnSuccess ->{
                    hideLoader()
                    hideNoFavRecipeUi()
                    onSuccessOfRecipeData(it.data)
                }
            }
        }
    }

    private fun onSuccessOfRecipeData(data: List<FavoriteRecipeDataModel>) {
        fragRecipeAdapter?.differ?.submitList(data)
        fragRecipeAdapter?.notifyDataSetChanged()
    }

    private fun hideLoader() {
        loaderView?.hideLoader()
        binding?.loaderView?.visibility = View.GONE
    }

    private fun showLoader() {
        loaderView?.showLoader()
        binding?.loaderView?.visibility = View.VISIBLE
    }

    private val recipeDetailAcitivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { recipeFavViewModel.getRecipes() }

    private fun setRecipeRecyclerView() {
        binding?.let {binding ->
            fragRecipeAdapter = FavoriteRecipeFragAdapter{data ->
                onItemClicked(data)
            }
            fragRecipeAdapter?.setContextualMenuCallback(object :FavoriteRecipeFragAdapter.ContextualMenuClickCallback{
                override fun onDeleteOptionClicked() {
                    fragRecipeAdapter?.differ?.currentList?.forEach {
                        if(it.isContextualModeSelected){
                            recipeFavViewModel.removeFavRecipeById(it.recipeDataModel.id)
                        }
                        recipeFavViewModel.getRecipes()
                    }
                }

            })
            binding.recipeRv.apply {
                adapter = fragRecipeAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragRecipeAdapter?.clearContextualMode()
    }

    private fun onItemClicked(data: RecipeDataModel) {
        val recipeDetailActivityIntent = Intent(requireContext(), RecipeDetailActivity::class.java)
        recipeDetailActivityIntent.putExtra(RecipeDetailActivity.RECIPE_DETAIL_DATA,data)
        recipeDetailAcitivityLauncher.launch(recipeDetailActivityIntent)
    }

    private fun addLoaderView() {
        loaderView = LoaderWidget(requireContext())
        val layoutParams = ActionBar.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        loaderView?.layoutParams = layoutParams
        binding?.loaderView?.addView(loaderView)
    }

}