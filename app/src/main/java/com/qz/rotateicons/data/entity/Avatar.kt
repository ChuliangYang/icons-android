package com.qz.rotateicons.data.entity

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import com.qz.rotateicons.utils.dpToPx

data class Avatar(var head: Bitmap?, val name:String, val describtion:String,val width:Float=50f, val height:Float=50f)

fun Avatar.calculateLayoutParam(context: Context): ViewGroup.LayoutParams{
    return ViewGroup.LayoutParams(width.dpToPx(context).toInt(),height.dpToPx(context).toInt())
}

fun Avatar.recycle(){
    head?.recycle()
    head=null
}