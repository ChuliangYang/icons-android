package com.qz.rotateicons.utils

import android.content.Context
import android.util.Log
import com.qz.rotateicons.data.entity.CS
import com.qz.rotateicons.data.entity.FB
import com.qz.rotateicons.data.entity.MX
import com.qz.rotateicons.data.entity.ReportForm
import com.qz.rotateicons.data.local.RotateIconLocalDataSourceImpl
import com.qz.rotateicons.data.remote.RotateIconRemoteDataSourceImpl
import com.qz.rotateicons.data.repos.RotateIconRepositoryImpl
import com.qz.rotateicons.rx.RotateLocalSourceImpl
import com.qz.rotateicons.rx.RotateRemoteSourceImpl
import com.qz.rotateicons.rx.RotateRepoRx

object InjectionUtil {
    fun injectRotateIconRepo(context: Context): RotateIconRepositoryImpl {
        return RotateIconRepositoryImpl(RotateIconLocalDataSourceImpl(context), RotateIconRemoteDataSourceImpl(context))
    }

    fun injectRotateRepoRx(context: Context):RotateRepoRx{
        return RotateRepoRx(RotateLocalSourceImpl(context), RotateRemoteSourceImpl(),context.applicationContext)
    }

    fun injectCSPlatform(): CS {
        return object : CS() {
            override fun reportFromCSForm(reportForm: ReportForm) {
                Log.d("CS", reportForm.reportToPlatform())
            }
        }
    }

    fun injectFBPlatform(context: Context): FB {
        return object : FB(context) {
            override fun reportFromFBForm(reportForm: ReportForm, FB: FB) {
//                FB.logEvent(reportForm.reportToPlatform(), Bundle())
                Log.d("FB", reportForm.reportToPlatform())

            }

        }
    }

    fun injectMXPlatform(context: Context): MX {
        return object : MX(context) {
            override fun reportFromMXForm(reportForm: ReportForm, MX: MX) {
//                MX.track(reportForm.reportToPlatform())
                Log.d("MX", reportForm.reportToPlatform())
            }
        }
    }
}