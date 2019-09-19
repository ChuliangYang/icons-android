package com.qz.rotateicons

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ItemBindingViewHolder<T>(val itemViewBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
    fun bindData(variableId: Int, data: T){
        itemViewBinding.setVariable(variableId,data)
        itemViewBinding.executePendingBindings()
    }
}