package com.qz.rotateicons.rx

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.qz.rotateicons.R
import com.qz.rotateicons.RotateIconActionListener
import com.qz.rotateicons.databinding.FragmentRotateIconBinding
import com.qz.rotateicons.databinding.FragmentRotateIconRxBinding
import com.qz.rotateicons.utils.fetchViewModel
import kotlin.concurrent.thread

class RotateIconFragmentRx : Fragment() {
    private lateinit var viewDataBinding: FragmentRotateIconRxBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding= FragmentRotateIconRxBinding.inflate(inflater,container,false).apply {
            viewmodel = (activity as? AppCompatActivity)?.fetchViewModel(RotateViewModelRx::class.java)
            listener = object : RotateIconActionListener {
                override fun onAvatarClick(view: View, index:Int) {
                    val bounce = AnimationUtils.loadAnimation(context, R.anim.anim_bounce)
                    view.startAnimation(bounce)
                    viewmodel?.reportItemEvent(index)
                }
            }
            lifecycleOwner = this@RotateIconFragmentRx
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel?.apply {
            startRotate.observe(this@RotateIconFragmentRx, Observer {
                if (it == true){
                    viewDataBinding.rotateIcons.startAnimate()
                } else{
                    viewDataBinding.rotateIcons.stopAnimate()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.viewmodel?.stop()
    }

    companion object {
        fun newInstance() = RotateIconFragmentRx().apply {
        }
    }

}