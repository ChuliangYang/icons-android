package com.qz.rotateicons

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.qz.rotateicons.data.repos.IRotateIconRepository
import com.qz.rotateicons.utils.InjectionUtil

class ViewModelFactory private constructor(
    private val rotateIconRepository: IRotateIconRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RotateIconViewModel::class.java) ->
                    RotateIconViewModel(rotateIconRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    InjectionUtil.injectRotateIconRepo(application)
                )
                    .also { INSTANCE = it }
            }
    }
}