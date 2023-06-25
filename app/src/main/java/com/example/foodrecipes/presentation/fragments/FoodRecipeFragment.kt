package com.example.foodrecipes.presentation.fragments

import android.app.ActionBar.LayoutParams
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFoodRecipeBinding
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDomainModel
import com.example.foodrecipes.domain.usecase.GetRecipeByQueryUseCase
import com.example.foodrecipes.domain.utils.ApiErrorMessage
import com.example.foodrecipes.presentation.activities.RecipeDetailActivity
import com.example.foodrecipes.presentation.adapter.RecipeFragAdapter
import com.example.foodrecipes.presentation.viewmodels.FoodRecipeViewModel
import com.example.foodrecipes.presentation.widget.LoaderWidget
import com.example.network.ResultEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodRecipeFragment : Fragment(R.layout.fragment_food_recipe) {
    var binding: FragmentFoodRecipeBinding? = null
    private var fragRecipeAdapter:RecipeFragAdapter? = null
    private var loaderView:LoaderWidget? = null

    private val foodRecipeViewModel :FoodRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodRecipeViewModel.getRecipes()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFoodRecipeBinding.bind(view)
        setOptionsMenu()
        initUi()
        observeChanges()
    }

    private fun setOptionsMenu(){
          val menuHost : MenuHost = requireActivity()
          menuHost.addMenuProvider(object:MenuProvider{
              override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                  menuInflater.inflate(R.menu.recipe_menu,menu)
                  val searchMenu = menu.findItem(R.id.menu_search)
                  val searchView = searchMenu.actionView as androidx.appcompat.widget.SearchView?
                  searchView?.isSubmitButtonEnabled = true
                  searchView?.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
                      override fun onQueryTextSubmit(p0: String?): Boolean {
                          val queryText = p0
                          if(!TextUtils.isEmpty(queryText) && queryText!!.length > 2){
                              foodRecipeViewModel.getRecipByQuery(getRecipeByQueryUseCaseParams(queryText))
                          }
                          searchView.clearFocus()
                          return false
                      }

                      override fun onQueryTextChange(p0: String?): Boolean {
                          return true
                      }

                  })

              }

              override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                  return when(menuItem.itemId){
                     R.id.menu_search -> {
                          true
                     }
                     else ->{
                          false

                     }
                  }

              }

          },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

    private fun getRecipeByQueryUseCaseParams(queryText: String): GetRecipeByQueryUseCase.GetRecipeByQueryUseCaseDataParams {
        return GetRecipeByQueryUseCase.GetRecipeByQueryUseCaseDataParams(query = queryText)
    }

    private fun observeChanges(){
        foodRecipeViewModel.recipeData.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    showNoInternetUi()
                    hideLoader()
                }
                ResultEvent.OnLoading ->{
                    showLoader()
                }
                is ResultEvent.OnSuccess ->{
                    hideLoader()
                    hideNoInternetUi()
                    onSuccessOfRecipeData(it.data)
                }
            }
        }
    }

    private fun showNoInternetUi() {
        binding?.noInternetConnectionUi?.noInternetTv?.text = ApiErrorMessage.UNKNOWN_ERROR
        binding?.noInternetConnectionUi?.parent?.visibility = View.VISIBLE
    }
    private fun hideNoInternetUi(){
        binding?.noInternetConnectionUi?.parent?.visibility = View.GONE

    }

    private fun hideLoader() {
        loaderView?.hideLoader()
        binding?.loaderView?.visibility = View.GONE
    }

    private fun showLoader() {
        loaderView?.showLoader()
        binding?.loaderView?.visibility = View.VISIBLE
    }

    private fun onSuccessOfRecipeData(data: RecipeDomainModel) {
        fragRecipeAdapter?.differ?.submitList(data.recipes)
    }

    private fun initUi() {
        addLoaderView()
        setRecipeRecyclerView()
         setClickListener()
    }


    private fun setClickListener(){
        binding?.floatingActionButton?.setOnClickListener {
            goToRecipeFilterFragment()
        }
    }

    private fun goToRecipeFilterFragment() {
        setFragmentResultListener(RecipeFilterBottomSheetFrag.FRAGMENT_SET_RES_KEY){key,bundle ->
            clearFragmentResultListener(RecipeFilterBottomSheetFrag.FRAGMENT_SET_RES_KEY)
            val mealType = bundle.getString(RecipeFilterBottomSheetFrag.MEAL_TYPE)
            val dietType = bundle.getString(RecipeFilterBottomSheetFrag.DIET_TYPE)
            foodRecipeViewModel.applyFilter(mealType,dietType)
        }
        findNavController().navigate(R.id.action_foodRecipeFragment_to_recipeFilterBottomSheetFrag)
    }

    private fun addLoaderView() {
        loaderView = LoaderWidget(requireContext())
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        loaderView?.layoutParams = layoutParams
        binding?.loaderView?.addView(loaderView)
    }

    private fun setRecipeRecyclerView() {
        binding?.let {binding ->
             fragRecipeAdapter = RecipeFragAdapter{data ->
                onItemClicked(data)
            }
           binding.recipeRv.apply {
               adapter = fragRecipeAdapter
               layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
           }
        }
    }

    private fun onItemClicked(data: RecipeDataModel) {
        val recipeDetailActivityIntent = Intent(requireContext(),RecipeDetailActivity::class.java)
        recipeDetailActivityIntent.putExtra(RecipeDetailActivity.RECIPE_DETAIL_DATA,data)
        requireContext().startActivity(recipeDetailActivityIntent)
    }

}