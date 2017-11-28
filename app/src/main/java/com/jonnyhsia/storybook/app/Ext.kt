package com.jonnyhsia.storybook.app

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jonnyhsia.storybook.BuildConfig

/**
 * 替换 Fragment
 */
fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

/**
 * 日志 Debug
 */
fun Any.logd(msg: String, tag: String = javaClass.simpleName) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, msg)
    }
}

/**
 * 日志 Error
 */
fun Any.loge(e: Throwable, msg: String? = null, tag: String = javaClass.simpleName) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg ?: e.message, e)
    }
}

/**
 * 检查字符串为空
 * @param checkBlankSpace 是否需要检查空白格
 */
fun String?.checkEmpty(checkBlankSpace: Boolean = false) = if (checkBlankSpace) {
    isNullOrBlank()
} else {
    isNullOrEmpty()
}

/**
 * 检查字符串非空
 */
fun String?.checkNotEmpty(checkBlankSpace: Boolean = false) = !checkEmpty(checkBlankSpace)