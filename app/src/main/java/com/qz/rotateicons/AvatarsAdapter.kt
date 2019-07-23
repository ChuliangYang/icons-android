package com.qz.rotateicons

import android.support.annotation.MainThread
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.databinding.ItemAvatarBinding
import kotlinx.coroutines.*

class AvatarsAdapter(private var avatars: MutableList<Avatar>) : RecyclerView.Adapter<ItemBindingViewHolder<Avatar>>() {


    override fun getItemCount(): Int {
        return avatars.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemBindingViewHolder<Avatar> {
        return ItemBindingViewHolder(ItemAvatarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(itemViewHolder: ItemBindingViewHolder<Avatar>, position: Int) {
        itemViewHolder.bindData(BR.avatarItem, avatars[position])
    }

    fun replaceData(avatars: List<Avatar>) {
        this.avatars.clear()
        this.avatars.addAll(avatars)
        notifyDataSetChanged()
    }

}

class AvatarsDiffAdapter(private var avatars: MutableList<Avatar>) :
    RecyclerView.Adapter<ItemBindingViewHolder<Avatar>>() {


    override fun getItemCount(): Int {
        return avatars.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemBindingViewHolder<Avatar> {
        return ItemBindingViewHolder(ItemAvatarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(itemViewHolder: ItemBindingViewHolder<Avatar>, position: Int) {
        itemViewHolder.bindData(BR.avatarItem, avatars[position])
    }

    fun updateDataSyc(avatars: List<Avatar>) {
        //同步比对
        DiffUtil.calculateDiff(AvatarsDiffer(this.avatars, avatars)).let {
            this.avatars = avatars.toMutableList()
            it.dispatchUpdatesTo(this)
        }
    }

    @MainThread
    suspend fun updateDataAsync(avatars: List<Avatar>) {
        var result:DiffUtil.DiffResult?=null
        withContext(Dispatchers.Default) {
            result=DiffUtil.calculateDiff(AvatarsDiffer(this@AvatarsDiffAdapter.avatars, avatars))
        }
        this.avatars = avatars.toMutableList()
        result?.dispatchUpdatesTo(this)
    }
}


class AvatarListAdapter:ListAdapter<Avatar,ItemBindingViewHolder<Avatar>>(object:DiffUtil.ItemCallback<Avatar>(){
    override fun areItemsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return oldItem == newItem
    }
    //直接返回true时表示只靠areItemsTheSame做判断,true为直接复用,false为重bind
    override fun areContentsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return true
    }
}){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemBindingViewHolder<Avatar> {
        return ItemBindingViewHolder(ItemAvatarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(itemViewHolder: ItemBindingViewHolder<Avatar>, position: Int) {
        itemViewHolder.bindData(BR.avatarItem, getItem(position))
    }

}