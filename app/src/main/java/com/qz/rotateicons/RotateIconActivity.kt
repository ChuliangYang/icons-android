package com.qz.rotateicons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        AvatarListFragment.newInstance()
}