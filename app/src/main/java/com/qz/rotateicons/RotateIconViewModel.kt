package com.qz.rotateicons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import androidx.paging.PagedList
import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.data.local.IRotateIconLocalDatasource
import com.qz.rotateicons.data.repos.IRotateIconRepository
import com.qz.rotateicons.utils.resourceToBitmap

class RotateIconViewModel(private val rotateIconRepository: IRotateIconRepository) : ViewModel(),
    IRotateIconLocalDatasource.LoadAvatarsCallback {

    val avatarList = MutableLiveData<List<Avatar>>()
    val isLoading = MutableLiveData<Boolean>()
    val startRotate = MutableLiveData<Boolean>()
    val avatarPageList=MutableLiveData<PagedList<Avatar>>()
    fun start() {
        isLoading.value = true
        rotateIconRepository.getAvatars(this)
    }

    fun stop(){
        startRotate.value=false
        avatarList.value?.let {
            rotateIconRepository.clearAvatars(it)
        }
    }

    fun loadAvatars(){
        rotateIconRepository.getAvatars(object:IRotateIconLocalDatasource.LoadAvatarsCallback{
            override fun onAvatarsLoaded(avatars: List<Avatar>) {
                avatarList.value = avatars
                Handler().postDelayed({
                    avatarList.postValue(avatars.toMutableList().apply {
                        removeAt(0)
                        removeAt(3)
                    })
                },4000 )
            }

            override fun onAvatarsUnavailable() {

            }

        })
    }



    override fun onAvatarsLoaded(avatars: List<Avatar>) {
        avatarList.value = avatars
        isLoading.value = false
//        startRotate.value = true
    }

    override fun onAvatarsUnavailable() {
        isLoading.value = false
    }

    fun reportItemEvent(index: Int) {
//        rotateIconRepository.run {
//            transformReportForm(index).let {
//                when (index) {
//                    0 -> reportToCS(it)
//                    1 -> {
//                        reportToCS(it)
//                        reportToFB(it)
//                    }
//                    2 -> {
//                        reportToCS(it)
//                        reportToMX(it)
//                    }
//                    3 -> {
//                        reportToCS(it)
//                        reportToFB(it)
//                        reportToMX(it)
//                    }
//                    4->{
//                        reportToFB(it)
//                        reportToMX(it)
//                    }
//                    else-> {}
//                }
//            }
//        }
    }

}