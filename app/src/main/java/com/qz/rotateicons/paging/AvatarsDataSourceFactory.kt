package com.qz.rotateicons.paging

import android.content.Context
import androidx.paging.DataSource
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.paging.AvatarsDataSource

class AvatarsDataSourceFactory(val context: Context):DataSource.Factory<Int,Avatar>(){
    override fun create(): DataSource<Int, Avatar> {
        return AvatarsDataSource(context)
    }
}