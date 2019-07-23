package com.qz.rotateicons.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.qz.rotateicons.ViewModelFactory

fun <T : ViewModel> AppCompatActivity.fetchViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)


fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.beginTransaction().apply {
        replace(frameId, fragment)
    }.commit()
}