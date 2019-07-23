package com.qz.rotateicons.data.entity
import android.content.Context

//import android.content.Context
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.mixpanel.android.mpmetrics.MixpanelAPI
//
sealed class ReportPlatform {
    abstract fun report(reportForm: ReportForm)
}

abstract class CS : ReportPlatform() {
    override fun report(reportForm: ReportForm) {
        reportFromCSForm(reportForm)
    }

    abstract fun reportFromCSForm(reportForm: ReportForm)
}

abstract class FB(val context: Context) : ReportPlatform() {

    val FB = "FB"

    override fun report(reportForm: ReportForm) {
        reportFromFBForm(reportForm, this)
    }

    abstract fun reportFromFBForm(reportForm: ReportForm, FB: FB)
}

abstract class MX(val context: Context) : ReportPlatform() {

    val MX = "MX"

    override fun report(reportForm: ReportForm) {
        reportFromMXForm(reportForm, this)
    }

    abstract fun reportFromMXForm(reportForm: ReportForm, MX: MX)
}




abstract class ReportForm {
    abstract fun reportToPlatform(): String
}
