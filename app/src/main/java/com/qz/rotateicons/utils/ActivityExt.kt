package com.qz.rotateicons.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.qz.rotateicons.ViewModelFactory

fun <T : ViewModel> AppCompatActivity.fetchViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)


fun AppCompatActivity.replaceFragmentInActivity(fragment: androidx.fragment.app.Fragment, frameId: Int) {
    supportFragmentManager.beginTransaction().apply {
        replace(frameId, fragment)
    }.commit()
}