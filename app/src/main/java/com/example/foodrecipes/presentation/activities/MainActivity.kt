package com.example.foodrecipes.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    private var navController:NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setNavigationView()
    }

    private fun setNavigationView() {
        binding?.let {binding ->
            val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
            navHostFragment?.let {navHostFragment ->
                navController = navHostFragment.findNavController()
                navController?.let {navController ->
                    val appConfiguration = AppBarConfiguration(setOf(
                        R.id.foodRecipeFragment,
                        R.id.favoriteRecipeFragment,
                        R.id.jokeRecipeFragment
                    ))
                    binding.bottomNavigationView.setupWithNavController(navController)
                    setupActionBarWithNavController(navController,appConfiguration)
                }
            }


        }
    }

    override fun onNavigateUp(): Boolean {
        return navController?.navigateUp()?:false || super.onNavigateUp()
    }
}