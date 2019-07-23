package com.qz.rotateicons

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.widget.ImageView
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.data.entity.calculateLayoutParam
import com.qz.rotateicons.view.RotateIconViewGroup

object RotateIconBinding {
    @BindingAdapter("avatars", "onAvatarClickListener", requireAll = false)
    @JvmStatic fun setAvatars(rotateIcon: RotateIconViewGroup, avatars: List<Avatar>?, onAvatarClickListener:RotateIconActionListener?) {
        if(avatars!=null){
            rotateIcon.removeAllViews()
            avatars.forEachIndexed { index,avatar ->
                rotateIcon.addView(ImageView(rotateIcon.context).apply {
                    setImageBitmap(avatar.head)
                    layoutParams=avatar.calculateLayoutParam(context)
                    setOnClickListener {
                        onAvatarClickListener?.onAvatarClick(this,index)
                    }
                })
            }
        }else if(onAvatarClickListener!=null){
            for(i in 0 until rotateIcon.childCount){
                rotateIcon.getChildAt(i).setOnClickListener {
                    onAvatarClickListener.onAvatarClick(it,i)
                }
            }
        }
    }
}

object ItemRecyclerViewBinding{
    @BindingAdapter("bitmapSrc")
    @JvmStatic fun setBitmap(head:ImageView, bitmap:Bitmap){
        head.setImageBitmap(bitmap)
    }
}