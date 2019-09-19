package com.qz.rotateicons.rx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.qz.rotateicons.data.entity.Avatar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RotateViewModelRx(val rxRepoRx: RotateRepoRx) : RxViewModel() {
    val avatarPageList = MediatorLiveData<PagedList<Avatar>>()
    val isLoading = MutableLiveData<Boolean>()
    val startRotate = MutableLiveData<Boolean>()
    val sendToActivity = MutableLiveData<String>()
    val avatarList = MutableLiveData<List<Avatar>>()

    private var avatarPageListLiveData: LiveData<PagedList<Avatar>>? = null

    fun start() {
        autoDispose(rxRepoRx.getAvatars().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            isLoading.postValue(true)
        }.subscribeBy(onNext = {
            avatarList.value = it
            isLoading.value = false
            startRotate.value = true
        }, onError = {
            isLoading.value = false
        }))
//        sendToActivity.value="start"
    }

    fun loadAvatarPageList() {
        avatarPageListLiveData?.let {
            avatarPageList.removeSource(it)
        }

        avatarPageList.addSource(rxRepoRx.getAvatarPageList()) {
            avatarPageList.value = it
        }

    }

    fun stop() {
        avatarPageList.value?.let {
            autoDispose(
                rxRepoRx.clearAvatars(it).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {
                    startRotate.postValue(false)
                }.subscribeBy(onComplete = {

                }, onError = {

                })
            )
        }
    }

    fun reportItemEvent(index: Int) {
    }
}

open class RxViewModel : ViewModel() {
    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun autoDispose(disposable: Disposable) {
        disposables.add(disposable)
    }
}
