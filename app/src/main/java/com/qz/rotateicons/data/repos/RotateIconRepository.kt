package com.qz.rotateicons.data.repos

import com.qz.rotateicons.data.entity.Avatar
import com.qz.rotateicons.data.entity.ReportForm
import com.qz.rotateicons.data.local.IRotateIconLocalDatasource
import com.qz.rotateicons.data.remote.IRotateIconRemoteDataSource

interface IRotateIconRepository {
    fun getAvatars(callback: IRotateIconLocalDatasource.LoadAvatarsCallback)
    fun clearAvatars(avatars: List<Avatar>)
    fun reportToCS(reportForm: ReportForm)
    fun reportToFB(reportForm: ReportForm)
    fun reportToMX(reportForm: ReportForm)
    fun transformReportForm(index: Int): ReportForm
}


class RotateIconRepositoryImpl(private val localDatasource: IRotateIconLocalDatasource, private val remoteDataSource: IRotateIconRemoteDataSource) :
    IRotateIconRepository {

    override fun getAvatars(callback: IRotateIconLocalDatasource.LoadAvatarsCallback) {
        localDatasource.getAvatars(object : IRotateIconLocalDatasource.LoadAvatarsCallback {
            override fun onAvatarsLoaded(avatars: List<Avatar>) {
                callback.onAvatarsLoaded(avatars)
            }

            override fun onAvatarsUnavailable() {
                //we can try other datasource if possible
                callback.onAvatarsUnavailable()
            }
        })
    }

    override fun clearAvatars(avatars: List<Avatar>){
        localDatasource.removeAvatars(avatars)
    }

    override fun reportToCS(reportForm: ReportForm) {
        localDatasource.saveReportToConsole(reportForm)
    }

    override fun reportToFB(reportForm: ReportForm) {
        remoteDataSource.saveReportToFB(reportForm)
    }

    override fun reportToMX(reportForm: ReportForm) {
        remoteDataSource.saveReportToMX(reportForm)
    }

    override fun transformReportForm(index: Int): ReportForm {
        return object : ReportForm() {
            override fun reportToPlatform(): String {
                return "image${index + 1}_tapped"
            }
        }
    }
}