package com.qz.rotateicons.data.remote

import android.content.Context
import com.qz.rotateicons.data.entity.ReportForm
import com.qz.rotateicons.data.entity.ReportPlatform
import com.qz.rotateicons.utils.InjectionUtil

interface IRotateIconRemoteDataSource {
    fun saveReportToFB(reportForm: ReportForm)
    fun saveReportToMX(reportForm: ReportForm)
}

class RotateIconRemoteDataSourceImpl(var context: Context) : IRotateIconRemoteDataSource {
    init {
        //force use applicationContext to avoid memory leak
        context=context.applicationContext
    }

    val FB: ReportPlatform = InjectionUtil.injectFBPlatform(context)

    val MX: ReportPlatform = InjectionUtil.injectMXPlatform(context)

    override fun saveReportToFB(reportForm: ReportForm) {
        FB.report(reportForm)
    }

    override fun saveReportToMX(reportForm: ReportForm) {
        MX.report(reportForm)
    }
}