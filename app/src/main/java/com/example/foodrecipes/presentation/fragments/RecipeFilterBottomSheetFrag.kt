package com.example.foodrecipes.presentation.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.RecipeFilterLayoutBinding
import com.example.recipecore.domain.model.MealTypeDietTypeDataModel
import com.example.foodrecipes.domain.utils.Utility
import com.example.foodrecipes.presentation.viewmodels.RecipeFilterViewModel
import com.example.network.ResultEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFilterBottomSheetFrag() : BottomSheetDialogFragment(R.layout.recipe_filter_layout) {

    private var binding: RecipeFilterLayoutBinding? = null
    private var currentlySelectedMealDietTypeId:Pair<String,String> = Pair("","")


    private val recipeFilterViewModel: RecipeFilterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        recipeFilterViewModel.getMealDietType()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RecipeFilterLayoutBinding.bind(view)
        observeChanges()
        initUi()

    }
    private fun initUi(){
        binding?.applyTv?.setOnClickListener {
            onApplyButtonClicked()
        }
        binding?.applyTv?.background = Utility.getCustomGradient(20,"#303030")
    }

    private fun onApplyButtonClicked() {
       if(!TextUtils.isEmpty(currentlySelectedMealDietTypeId.first) && !TextUtils.isEmpty(currentlySelectedMealDietTypeId.second)){
           val bundle:Bundle = Bundle()
           val mealType = recipeFilterViewModel.getMealDietTypeDataModel()?.mealType?.get(currentlySelectedMealDietTypeId.first)
           val dietType = recipeFilterViewModel.getMealDietTypeDataModel()?.dietType?.get(currentlySelectedMealDietTypeId.second)
           recipeFilterViewModel.saveUserPref(mealType,dietType)
           bundle.putString(MEAL_TYPE,mealType?.title)
           bundle.putString(DIET_TYPE,dietType?.title)
           setFragmentResult(FRAGMENT_SET_RES_KEY,bundle)
           findNavController().popBackStack()
       }else{
           Toast.makeText(requireContext(),"Please select valid meal and diet",Toast.LENGTH_LONG).show()
       }
    }

    private fun observeChanges() {
        recipeFilterViewModel.mealDietTypeData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultEvent.OnFailure -> {}
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    recipeFilterViewModel.setMealDietTypeDataModel(it.data)
                    onSuccessOfMealDietTypeData(it.data)
                }
            }
        }
    }

    private fun onSuccessOfMealDietTypeData(data: MealTypeDietTypeDataModel){
        addMealtype(data)
        addDietType(data)
    }

    private fun addMealtype(data: MealTypeDietTypeDataModel) {
        binding?.mealTypeFlowLayout?.let { flowLayout ->
            flowLayout.removeAllViews()
            data.mealType.forEach {entry ->
                val meal = entry.value
                val itemView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.meal_type_chip_item, null, false)
                itemView.tag = entry.key
                val textView = itemView.findViewById<TextView>(R.id.chip_title)
                if(meal.isSelected){
                    currentlySelectedMealDietTypeId  = currentlySelectedMealDietTypeId.copy(first = itemView.tag as String)
                    textView.background = Utility.getCustomGradient(20, 1, "#303030", "#ffffff")
                    textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.ads_303030))
                }else{
                    textView.background = Utility.getCustomGradient(20,1,"#E7E7E7","#FFFFFF")
                }
                textView.text = meal.title
                itemView.setOnClickListener {
                    data.mealType.forEach { e-> e.value.isSelected = false }
                    meal.isSelected = true
                    addMealtype(data)
                   currentlySelectedMealDietTypeId = currentlySelectedMealDietTypeId.copy(first = it.tag as String)
                }
                flowLayout.addView(itemView)
            }
        }

    }

    private fun addDietType(data: MealTypeDietTypeDataModel){
        binding?.dietTypeFlowLayout?.let { flowLayout ->
            flowLayout.removeAllViews()
            data.dietType.forEach {entry ->
                val diet = entry.value
                val itemView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.meal_type_chip_item, null, false)
                itemView.tag = entry.key
                val textView = itemView.findViewById<TextView>(R.id.chip_title)
                if(diet.isSelected){
                    currentlySelectedMealDietTypeId  = currentlySelectedMealDietTypeId.copy(second = itemView.tag as String)
                    textView.background = Utility.getCustomGradient(20, 1, "#303030", "#ffffff")
                    textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.ads_303030))
                }else{
                    textView.background = Utility.getCustomGradient(20,1,"#E7E7E7","#FFFFFF")
                }
                textView.text = diet.title
                itemView.setOnClickListener {
                    data.dietType.forEach {e-> e.value.isSelected = false }
                    diet.isSelected = true
                   currentlySelectedMealDietTypeId =  currentlySelectedMealDietTypeId.copy(second = it.tag as String)
                    addDietType(data)
                }
                flowLayout.addView(itemView)
            }
        }
    }

    companion object{
        val FRAGMENT_SET_RES_KEY = "recipe_filer_key_result"
        val MEAL_TYPE = "meal_id"
        val DIET_TYPE = "diet_id"
    }


}