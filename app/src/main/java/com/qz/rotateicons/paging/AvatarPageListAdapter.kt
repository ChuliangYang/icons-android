package com.qz.rotateicons.paging

import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qz.rotateicons.R
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.databinding.ItemAvatarBinding

class AvatarPageListAdapter:PagedListAdapter<Avatar,ItemBindingViewHolder>(object : DiffUtil.ItemCallback<Avatar?>() {
    override fun areItemsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return true
    }
}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBindingViewHolder {
        return ItemBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_avatar,parent, false))
    }

    override fun onBindViewHolder(holder: ItemBindingViewHolder, position: Int) {
        holder.bindData(BR.avatarItem,getItem(position))
    }

}

class ItemBindingViewHolder(private val dataBinding:ViewDataBinding): RecyclerView.ViewHolder(dataBinding.root){
    fun <T> bindData(id:Int,data:T){
        dataBinding.setVariable(id,data)
        dataBinding.executePendingBindings()
    }
}