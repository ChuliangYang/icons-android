package com.qz.rotateicons.paging

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.qz.rotateicons.R
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.utils.resourceToBitmap

class AvatarsDataSource(val context: Context):PageKeyedDataSource<Int,Avatar>(){

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Avatar>
    ) {

        val list=mutableListOf<Avatar>().apply{
            for(i in 0 until params.requestedLoadSize){
                add(Avatar(context.resourceToBitmap(R.mipmap.ic_face2),"michael",".net"))
            }
        }

        callback.onResult(list,null,2)
        Log.e("AvatarsDataSource","page=1")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Avatar>) {
        Log.e("AvatarsDataSource","page=${params.key}")
        val list=mutableListOf<Avatar>().apply{
            for(i in 0 until params.requestedLoadSize){
                add(Avatar(context.resourceToBitmap(R.mipmap.ic_face2),"ken","java"))
            }
        }
        callback.onResult(list, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Avatar>) {
    }

}