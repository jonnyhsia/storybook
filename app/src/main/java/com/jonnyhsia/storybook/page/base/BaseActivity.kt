package com.jonnyhsia.storybook.page.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Activity 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 获取 Activity 的参数
     */
    fun arguments(): Bundle {
        return if (intent?.extras == null) {
            Bundle()
        } else {
            intent.extras
        }
    }

    fun jump(uri: String) {
        // TODO: 页面路由跳转
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getParamOrDefault(key: String, default: T): T {
        val param: Any = when (default) {
            is Int -> intent.getIntExtra(key, default)
            is Double -> intent.getDoubleExtra(key, default)
            is Long -> intent.getLongExtra(key, default)
            is Float -> intent.getFloatExtra(key, default)
            is String -> intent.getStringExtra(key) ?: ""
            else -> throw IllegalStateException("Type is invalid")
        }
        return param as T
    }
}