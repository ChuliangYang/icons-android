package com.qz.rotateicons

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

 class ItemBindingViewHolder<T>(val itemViewBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
    fun bindData(variableId: Int, data: T){
        itemViewBinding.setVariable(variableId,data)
        itemViewBinding.executePendingBindings()
    }
}