package com.qz.rotateicons

import android.support.v7.util.DiffUtil
import com.qz.rotateicons.data.entity.Avatar

abstract class ListDiffer <T>(val oldList:List<T>, val newList:List<T>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

}

class AvatarsDiffer(val moldList: List<Avatar>, val mnewList: List<Avatar>):ListDiffer<Avatar>(moldList,mnewList){
    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        //快速判断出需重bind的item(因此经常用id).用此方法快速判别item是否一样,不一样的话马上重新bind, 一样的话还要走areContentsTheSame进一步判断内容是否一致.
        return moldList[p0] == mnewList[p1]
    }

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return moldList[p0] == mnewList[p1]
    }
    //按需重写, 把content的field不同通过flag区分, 然后在onBindViewHolder(...payload)中根据flag更新field
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}