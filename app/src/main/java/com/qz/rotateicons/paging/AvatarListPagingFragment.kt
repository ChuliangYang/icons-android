package com.qz.rotateicons.paging

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qz.rotateicons.AvatarListAdapter
import com.qz.rotateicons.R
import com.qz.rotateicons.RotateIconViewModel
import com.qz.rotateicons.rx.RotateViewModelRx
import com.qz.rotateicons.utils.fetchViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.coroutines.*

class AvatarListPagingFragment: androidx.fragment.app.Fragment() {
    private var viewModel: RotateViewModelRx?=null
    private val mainScope= MainScope()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel=(activity as? AppCompatActivity)?.fetchViewModel(RotateViewModelRx::class.java)
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.loadAvatarPageList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun setUpRecyclerView() {
        rv_list.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter= AvatarPageListAdapter()
        }

        viewModel?.avatarPageList?.observe(this, Observer {
            (rv_list.adapter as AvatarPageListAdapter).submitList(it)
        })
    }

    companion object {
        fun newInstance() = AvatarListPagingFragment().apply {
        }
    }

}