package com.qz.rotateicons

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qz.rotateicons.utils.fetchViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.coroutines.*

class AvatarListFragment:Fragment() {
    private var viewModel: RotateIconViewModel?=null
    private val mainScope= MainScope()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel=(activity as? AppCompatActivity)?.fetchViewModel(RotateIconViewModel::class.java)
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.loadAvatars()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun setUpRecyclerView() {
        rv_avatars.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=AvatarListAdapter()
        }

        viewModel?.avatarList?.observe(this, Observer {
            it?.let {
                 (rv_avatars.adapter as? AvatarListAdapter)?.submitList(it)
            }
        })
    }

    companion object {
        fun newInstance() = AvatarListFragment().apply {
        }
    }

}