package com.jonnyhsia.storybook.kit

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import com.jonnyhsia.storybook.app.App

object AppKit {

    /**
     * 判断网络可不可用
     * @return true为可用
     */
    fun isNetworkAvailable(): Boolean {
        val cm = App.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val info = cm?.activeNetworkInfo ?: return false
        return info.isAvailable && info.isConnected
    }


    /**
     * 获取版本号
     */
    fun getVersion(context: Context): String = try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        pi.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        "版本未知"
    }


    /**
     * 获取版本代号
     * @param context
     * @return
     */
    fun getVersionCode(context: Context) = try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        pi.versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        0
    }
}