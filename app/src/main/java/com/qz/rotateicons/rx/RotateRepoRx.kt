package com.qz.rotateicons.rx

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qz.rotateicons.R
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.data.entity.ReportForm
import com.qz.rotateicons.data.entity.recycle
import com.qz.rotateicons.paging.AvatarsDataSourceFactory
import com.qz.rotateicons.utils.resourceToBitmap
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RotateRepoRx(private val localsource: RotateLocalSource, private val remoteSource: RotateRemoteSource,private val application: Context){
        fun getAvatars():Observable<List<Avatar>>{
            return Observable.create {
                it.onNext(localsource.getAvatars())
            }
        }

        fun clearAvatars(avatars: List<Avatar>):Observable<Unit>{
            return Observable.create {
                localsource.removeAvatars(avatars)
                it.onComplete()
            }
        }


        fun getAvatarPageList():LiveData<PagedList<Avatar>>{
           return LivePagedListBuilder(AvatarsDataSourceFactory(application), PagedList.Config.Builder().setInitialLoadSizeHint(10).setPageSize(5).build()).build()
        }

//        fun report(reportForm: ReportForm):Observable<Response>{
//           return  Observable.create<Response> {
//                remoteSource.getCall().enqueue(object : Callback {
//                    override fun onFailure(call: Call, e: IOException) {
//                        it.onError(e)
//                    }
//
//                    override fun onResponse(call: Call, response: Response) {
//                        it.onNext(response)
//                    }
//                })
//            }
//        }
}


interface RotateLocalSource{
    fun getAvatars():List<Avatar>
    fun removeAvatars(avatars: List<Avatar>)
}

interface RotateRemoteSource


class RotateLocalSourceImpl(var context: Context):RotateLocalSource{
    init {
        context=context.applicationContext
    }
    override fun getAvatars(): List<Avatar> {
        return mutableListOf<Avatar>().apply{
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face1),"James","html"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face2),"michael",".net"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face3),"lili","c#"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face4),"kang","c++"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face5),"carlos","kotlin"))
            add(Avatar(context.resourceToBitmap(R.mipmap.ic_face6),"xiaoming","java"))
        }
    }

    override fun removeAvatars(avatars: List<Avatar>) {
        avatars.forEach {
            it.recycle()
        }
    }

}

class RotateRemoteSourceImpl:RotateRemoteSource