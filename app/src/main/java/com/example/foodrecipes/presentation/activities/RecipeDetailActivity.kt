package com.example.foodrecipes.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.ActivityRecipeDetailBinding
import com.example.recipecore.domain.model.RecipeDataModel
import com.example.recipecore.domain.model.RecipeDetailDataModel
import com.example.foodrecipes.domain.utils.Utility.sdk33down
import com.example.foodrecipes.presentation.adapter.RecipeDetailViewPagerAdapter
import com.example.foodrecipes.presentation.viewmodels.RecipeDetailViewModel
import com.example.network.ResultEvent
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {
    private var activityRecipeDetailBinding:ActivityRecipeDetailBinding? = null
    private var recipeDetailViewPagerAdapter:RecipeDetailViewPagerAdapter?  = null
    private val recipeDetailViewModel : RecipeDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipeDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_recipe_detail)
        readIntent()
        initUi()
    }

    private fun initUi() {
        setViewPager()
        setTabWithViewPager()
        observeChanges()
    }

    private fun setTabWithViewPager() {
        activityRecipeDetailBinding?.viewPager?.let{viewPager ->
            activityRecipeDetailBinding?.tablayout?.let {tabLayout ->
                TabLayoutMediator(tabLayout,viewPager){tab,pos ->
                        tab.text = recipeDetailViewPagerAdapter?.getList()?.get(pos)?.title?:""
                }.attach()
            }
        }
    }

    private fun observeChanges() {
        recipeDetailViewModel.recipeDetailViewPagerData.observe(this){
                when(it){
                    is ResultEvent.OnFailure -> {}
                    ResultEvent.OnLoading -> {}
                    is ResultEvent.OnSuccess -> {
                        onSuccessOfRecipeDetailData(it.data)
                    }
                }
        }
    }
    private fun onSuccessOfRecipeDetailData(data: List<RecipeDetailDataModel>) {
        recipeDetailViewPagerAdapter?.updateList(data)
    }

    private fun setViewPager() {
        activityRecipeDetailBinding?.viewPager?.let {viewpager ->
            recipeDetailViewPagerAdapter = RecipeDetailViewPagerAdapter(supportFragmentManager,lifecycle)
            viewpager.adapter = recipeDetailViewPagerAdapter
            viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }


    @Suppress
    private fun readIntent() {
         intent?.let {
             var data:RecipeDataModel? = null
              this.sdk33down({
                  data = it.getParcelableExtra(RECIPE_DETAIL_DATA)
              }){
                  data = it.getParcelableExtra(RECIPE_DETAIL_DATA,RecipeDataModel::class.java)
              }
             data?.let {
                 recipeDetailViewModel.setRecipeDataModel(it)
                recipeDetailViewModel.getRecipeDetailViewPagerData(it)
             }
         }
    }

    companion object{
        const val RECIPE_DETAIL_DATA = "recipe_detail_data"
        const val DATA = "bundle_data"
    }
}