package com.example.foodrecipes.domain.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

abstract  class CommonAdapter<D:Any>:RecyclerView.Adapter<CommonAdapter.ViewHolder>() {
    private val diffUtil = Utility.DiffUtil<D>()
    val differ = AsyncListDiffer(this,diffUtil)
    class ViewHolder(val viewbinding:ViewDataBinding):RecyclerView.ViewHolder(viewbinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          val binding:ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),viewType,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder.viewbinding,position)
    }

    override fun getItemCount(): Int  = differ.currentList.size
    override fun getItemViewType(position: Int): Int {
        return getlayout(position)
    }

    abstract fun bind(viewbinding: ViewDataBinding,position: Int)
    abstract fun getlayout(position: Int):Int


}