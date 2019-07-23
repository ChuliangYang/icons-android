package com.qz.rotateicons.data.local

import android.content.Context
import com.qz.rotateicons.R
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.data.entity.ReportForm
import com.qz.rotateicons.data.entity.ReportPlatform
import com.qz.rotateicons.data.entity.recycle
import com.qz.rotateicons.utils.InjectionUtil
import com.qz.rotateicons.utils.resourceToBitmap

interface IRotateIconLocalDatasource {
    interface LoadAvatarsCallback{
        fun onAvatarsLoaded(avatars:List<Avatar>)
        fun onAvatarsUnavailable()
    }

    fun getAvatars(callback: LoadAvatarsCallback)
    fun saveReportToConsole(reportForm: ReportForm)
    fun removeAvatars(avatars: List<Avatar>)
}

class RotateIconLocalDataSourceImpl(var context: Context): IRotateIconLocalDatasource {
    init {
        //force use applicationContext to avoid memory leak
        context=context.applicationContext
    }

    val CS: ReportPlatform = InjectionUtil.injectCSPlatform()

    override fun getAvatars(callback: IRotateIconLocalDatasource.LoadAvatarsCallback) {
        callback.onAvatarsLoaded(mutableListOf<Avatar>().apply{
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face1),"James","html"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face2),"michael",".net"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face3),"lili","c#"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face4),"kang","c++"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face5),"carlos","kotlin"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face6),"xiaoming","java"))
        })
    }

    override fun removeAvatars(avatars: List<Avatar>){
        avatars.forEach {
            it.recycle()
        }
    }

    override fun saveReportToConsole(reportForm: ReportForm){
        CS.report(reportForm)
    }
}