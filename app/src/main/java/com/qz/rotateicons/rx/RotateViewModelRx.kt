package com.qz.rotateicons.rx

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.util.Log
import com.qz.rotateicons.data.entity.Avatar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RotateViewModelRx(val rxRepoRx:RotateRepoRx):RxViewModel(){
    val avatarList = MutableLiveData<List<Avatar>>()
    val isLoading = MutableLiveData<Boolean>()
    val startRotate = MutableLiveData<Boolean>()
    val sendToActivity=MutableLiveData<String>()

    fun loadAvatars(){
        autoDispose(rxRepoRx.getAvatars().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
            avatarList.value=it
            Handler().postDelayed({
                avatarList.postValue(it.toMutableList().apply {
                    removeAt(0)
                    removeAt(3)
                })
            },4000 )
        },onError = {
            Log.e("123",it.message)
        }))
    }

    fun start() {
        autoDispose(rxRepoRx.getAvatars().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            isLoading.postValue(true)
        }.subscribeBy(onNext = {
            avatarList.value = it
            isLoading.value = false
            startRotate.value = true
        },onError = {
            isLoading.value = false
        }))
//        sendToActivity.value="start"

    }


    fun stop(){
        avatarList.value?.let {
            autoDispose(rxRepoRx.clearAvatars(it).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                startRotate.postValue(false)
            }.subscribeBy(onComplete = {

            },onError = {

            }))
        }
    }

    fun reportItemEvent(index: Int) {
    }
}

open class RxViewModel:ViewModel(){
    val disposables=CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun autoDispose(disposable: Disposable){
        disposables.add(disposable)
    }
}
