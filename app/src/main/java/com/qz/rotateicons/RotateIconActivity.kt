package com.qz.rotateicons

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.qz.rotateicons.paging.AvatarListPagingFragment
import com.qz.rotateicons.rx.RotateIconFragmentRx
import com.qz.rotateicons.rx.RotateViewModelRx
import com.qz.rotateicons.utils.replaceFragmentInActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RotateIconActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        replaceFragmentInActivity(findOrCreateViewFragment(),R.id.content)
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.content) ?:
        AvatarListPagingFragment.newInstance()
}