package com.jonnyhsia.storybook.app

import android.app.Application
import com.jonnyhsia.storybook.BuildConfig
import com.jonnyhsia.storybook.biz.Injection
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        // Model 的初始化与预加载
        Injection.initialize(this)
        Injection.preload()

        initCrashReport()
    }

    /**
     * 初始化 Bugly 崩溃上报
     */
    private fun initCrashReport() {
        CrashReport.setIsDevelopmentDevice(applicationContext, BuildConfig.DEBUG);
        CrashReport.initCrashReport(applicationContext)
    }

    companion object {
        var INSTANCE: App by Delegates.notNull()
            private set
    }
}