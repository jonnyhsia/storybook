package com.jonnyhsia.storybook.page.base

import android.os.Bundle

/**
 * 支持日夜间模式的 Activity
 */
abstract class DayNightActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 设置日夜间
    }
}