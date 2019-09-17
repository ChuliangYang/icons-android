package com.qz.rotateicons

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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

    override fun onResume() {
        super.onResume()
//        ViewModelProviders.of(this,ViewModelFactory.getInstance(this)).get(RotateViewModelRx::class.java).apply {
//            sendToActivity.observe(this@RotateIconActivity, Observer {
//                Toast.makeText(this@RotateIconActivity,it,Toast.LENGTH_SHORT).show()
//            })
//        }
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.content) ?:
        RotateIconFragmentRx.newInstance()
}