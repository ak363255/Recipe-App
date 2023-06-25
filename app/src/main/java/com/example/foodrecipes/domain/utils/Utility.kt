package com.example.foodrecipes.domain.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Build.VERSION
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.foodrecipes.R
import com.google.android.material.snackbar.Snackbar

object Utility {

    class DiffUtil<T : Any> : androidx.recyclerview.widget.DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem.equals(newItem)
    }


    fun getCustomGradient(cornerRadius: Int, color: String): GradientDrawable {
        val shapeGrey = GradientDrawable()
        shapeGrey.cornerRadius = dpToPx(cornerRadius).toFloat()
        shapeGrey.color = ColorStateList.valueOf(Color.parseColor(color))
        return shapeGrey
    }

    // todo get custom gradient with custom border and solid color
    fun getCustomGradient(
        cornerRadius: Int,
        stroke: Int,
        strokeColor: String,
        color: String
    ): GradientDrawable {
        val shapeGrey = GradientDrawable()
        shapeGrey.cornerRadius = dpToPx(cornerRadius).toFloat()
        shapeGrey.setStroke(dpToPx(stroke), Color.parseColor(strokeColor))
        shapeGrey.color = ColorStateList.valueOf(Color.parseColor(color))
        return shapeGrey
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    inline fun Context.sdk33down(down: () -> Unit, up: () -> Unit) {
        if (VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            down.invoke()
        } else {
            up.invoke()
        }
    }

    fun setHtmlText(textView: TextView, text: String?) {
        if (!TextUtils.isEmpty(text)) {
            try {
                val spanned = HtmlCompat.fromHtml(text!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
                textView.text = spanned
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun Context.hideForceKeyboard() {
        val imm =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.HIDE_IMPLICIT_ONLY,
            0
        )
    }

    fun Context.showSnackBar(view:View,message:String, color:Int){
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("OK",
            View.OnClickListener { snackbar.dismiss() })
        snackbar.setDuration(9000)
        val group = snackbar.getView()
        val textView = group.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setTextColor(color)
        group.setBackgroundColor(ContextCompat.getColor(this, R.color.ads_303030))
        val txtView = group.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        txtView.maxLines = 5
        snackbar.show()
    }
}