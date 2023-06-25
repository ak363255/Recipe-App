package com.example.foodrecipes.presentation.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.example.foodrecipes.databinding.LoaderViewLayoutBinding

class LoaderWidget(
    private val context:Context
):LinearLayout(context) {
    val rotationAnimatoin = RotateAnimation(0.0f,360.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    var binding:LoaderViewLayoutBinding? = null
    init {
        binding = LoaderViewLayoutBinding.inflate(LayoutInflater.from(context),this,true)
        rotationAnimatoin.repeatMode = Animation.INFINITE
        rotationAnimatoin.duration = 1000
        rotationAnimatoin.repeatCount = Animation.INFINITE
        binding?.loaderImg?.animation = rotationAnimatoin
    }

    private fun startAnimation(){
        rotationAnimatoin.start()
    }
    private fun stopAnimation(){
        rotationAnimatoin.cancel()
    }
    fun showLoader(){
        startAnimation()
        binding?.parent?.visibility = View.VISIBLE
    }
    fun hideLoader(){
        stopAnimation()
        binding?.parent?.visibility = View.GONE
    }
}