package com.qz.rotateicons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.qz.rotateicons.data.repos.IRotateIconRepository
import com.qz.rotateicons.rx.RotateRepoRx
import com.qz.rotateicons.rx.RotateViewModelRx
import com.qz.rotateicons.utils.InjectionUtil

//class ViewModelFactory private constructor(
//    private val rotateIconRepository: IRotateIconRepository
//) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>) =
//        with(modelClass) {
//            when {
//                isAssignableFrom(RotateIconViewModel::class.java) ->
//                    RotateIconViewModel(rotateIconRepository)
//                isAssignableFrom(RotateIconViewModel::class.java) ->
//                    RotateIconViewModel(rotateIconRepository)
//                else ->
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//
//    companion object {
//
//        private var INSTANCE: ViewModelFactory? = null
//
//        fun getInstance(application: Application) =
//            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
//                INSTANCE ?: ViewModelFactory(
//                    InjectionUtil.injectRotateIconRepo(application)
//                )
//                    .also { INSTANCE = it }
//            }
//    }
//}


class ViewModelFactory(
    var context: Context
):ViewModelProvider.NewInstanceFactory(){
    val rotateIconRepository: IRotateIconRepository by lazy {
        InjectionUtil.injectRotateIconRepo(context)
    }

    val rotateRepoRxRx: RotateRepoRx by lazy {
        InjectionUtil.injectRotateRepoRx(context)
    }


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        modelClass.apply {
           return  when{
                isAssignableFrom(RotateIconViewModel::class.java) ->
                    RotateIconViewModel(rotateIconRepository)
                isAssignableFrom(RotateViewModelRx::class.java)->
                    RotateViewModelRx(rotateRepoRxRx)
                else ->
                    throw java.lang.IllegalArgumentException()
            } as T
        }
    }

    companion object{
        @Volatile var INSTANCE:ViewModelFactory?=null

        fun getInstance(context: Context):ViewModelFactory{
            if(INSTANCE==null){
                synchronized(this){
                    if (INSTANCE==null){
                        INSTANCE=ViewModelFactory(context.applicationContext)
                    }
                }
            }

            return INSTANCE!!
        }
    }

}