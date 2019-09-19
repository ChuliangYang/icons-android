package com.qz.rotateicons

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.qz.rotateicons.databinding.FragmentRotateIconBinding
import com.qz.rotateicons.utils.fetchViewModel
import kotlin.concurrent.thread

class RotateIconFragment : androidx.fragment.app.Fragment() {
    private lateinit var viewDataBinding: FragmentRotateIconBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding= FragmentRotateIconBinding.inflate(inflater,container,false).apply {
            viewmodel = (activity as? AppCompatActivity)?.fetchViewModel(RotateIconViewModel::class.java)
            listener = object : RotateIconActionListener {
                override fun onAvatarClick(view: View, index:Int) {
                    val bounce = AnimationUtils.loadAnimation(context, R.anim.anim_bounce)
                    view.startAnimation(bounce)
                    viewmodel?.reportItemEvent(index)
                }
            }
            lifecycleOwner = this@RotateIconFragment
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel?.apply {
            startRotate.observe(this@RotateIconFragment, Observer {
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
        fun newInstance() = RotateIconFragment().apply {
        }
    }

}