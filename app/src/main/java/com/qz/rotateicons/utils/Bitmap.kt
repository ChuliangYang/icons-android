package com.qz.rotateicons.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun Context.resourceToBitmap(imageId:Int): Bitmap {
    return BitmapFactory.decodeResource(resources,imageId)
}