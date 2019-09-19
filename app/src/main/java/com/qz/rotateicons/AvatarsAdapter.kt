package com.qz.rotateicons

import androidx.annotation.MainThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qz.rotateicons.data.entity.Avatar
import kotlinx.android.synthetic.main.item_avatar.view.*
import kotlinx.coroutines.*

class AvatarsAdapter(private var avatars: MutableList<Avatar>) : RecyclerView.Adapter<ItemBindingViewHolder<Avatar>>() {


    override fun getItemCount(): Int {
        return avatars.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemBindingViewHolder<Avatar> {
        return ItemBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_avatar,parent, false))
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
        return ItemBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_avatar,parent, false))
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
        var result: DiffUtil.DiffResult?=null
        withContext(Dispatchers.Default) {
            result=DiffUtil.calculateDiff(AvatarsDiffer(this@AvatarsDiffAdapter.avatars, avatars))
        }
        this.avatars = avatars.toMutableList()
        result?.dispatchUpdatesTo(this)
    }
}


class AvatarListAdapter: ListAdapter<Avatar, ItemBindingViewHolder<Avatar>>(object:DiffUtil.ItemCallback<Avatar>(){
    override fun areItemsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return oldItem == newItem
    }
    //直接返回true时表示只靠areItemsTheSame做判断,true为直接复用,false为重bind
    override fun areContentsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return true
    }
}){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemBindingViewHolder<Avatar> {
        return ItemBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_avatar,parent, false))
    }

    override fun onBindViewHolder(itemViewHolder: ItemBindingViewHolder<Avatar>, position: Int) {
        itemViewHolder.bindData(BR.avatarItem, getItem(position))
    }

}

class testListAdapter:ListAdapter<Avatar,TestViewHolder>(object : DiffUtil.ItemCallback<Avatar?>() {
    override fun areItemsTheSame(p0: Avatar, p1: Avatar): Boolean {
        return p0==p1
    }

    override fun areContentsTheSame(p0: Avatar, p1: Avatar): Boolean {
        return true
    }
}) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TestViewHolder {
        return TestViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_avatar,p0,false))
    }

    override fun onBindViewHolder(p0: TestViewHolder, p1: Int) {
        p0.itemView.apply {
            tv_test.text = "123"

        }
    }
}


class TestViewHolder(val view: View):RecyclerView.ViewHolder(view)

