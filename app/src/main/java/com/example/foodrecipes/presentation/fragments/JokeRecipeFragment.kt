package com.example.foodrecipes.presentation.fragments

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFoodRecipeBinding
import com.example.foodrecipes.databinding.FragmentJokeRecipeBinding
import com.example.foodrecipes.domain.utils.Utility
import com.example.foodrecipes.presentation.viewmodels.JokeRecipeViewModel
import com.example.foodrecipes.presentation.widget.LoaderWidget
import com.example.network.ResultEvent
import com.example.recipecore.data.model.RecipeJokeDataModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeRecipeFragment : Fragment(R.layout.fragment_joke_recipe) {
    private var binding: FragmentJokeRecipeBinding? = null
    private val jokeRecipeViewModel: JokeRecipeViewModel by viewModels()
    private var loaderView:LoaderWidget? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jokeRecipeViewModel.getRandomFoodJodke()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJokeRecipeBinding.bind(view)
        initUi()
        observeChanges()

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

    private fun observeChanges() {
        jokeRecipeViewModel.recipeJokeData.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    hideLoader()
                    showNoJokeFoundUi()
                }
                ResultEvent.OnLoading -> {
                    showLoader()
                }
                is ResultEvent.OnSuccess -> {
                    hideLoader()
                    hideNoJokeFoundUi()
                    onSuccessOfJokeData(it.data)
                }
            }
        }
    }

    private fun onSuccessOfJokeData(data: RecipeJokeDataModel) {
        binding?.jokeTv?.text = data.text
    }

    private fun hideNoJokeFoundUi() {
        binding?.noInternetConnectionUi?.parent?.visibility = View.GONE
    }

    private fun showNoJokeFoundUi() {
        binding?.noInternetConnectionUi?.noInternetTv?.text = "No Joke Found"
        binding?.noInternetConnectionUi?.parent?.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loaderView?.hideLoader()
        binding?.loaderView?.visibility = View.GONE
    }

    private fun showLoader() {
        loaderView?.showLoader()
        binding?.loaderView?.visibility = View.VISIBLE
    }

    private fun initUi() {
          addLoaderView()
        binding?.jokeTv?.background = Utility.getCustomGradient(12,"#303030")

    }
}