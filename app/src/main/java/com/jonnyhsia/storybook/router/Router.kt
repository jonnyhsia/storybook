package com.jonnyhsia.storybook.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.ArrayMap
import com.jonnyhsia.storybook.biz.Injection
import com.jonnyhsia.storybook.biz.base.BaseLogic
import com.jonnyhsia.storybook.biz.profile.ProfileRepository
import com.jonnyhsia.storybook.kit.checkNotEmpty
import com.jonnyhsia.storybook.kit.logd
import com.jonnyhsia.storybook.page.auth.AuthActivity
import com.jonnyhsia.storybook.page.main.MainActivity
import com.jonnyhsia.storybook.page.pagenotfound.PageNotFoundActivity

/**
 * 页面路由
 */
@Suppress("MemberVisibilityCanPrivate", "UNUSED_PARAMETER")
object Router {

    /**
     * 暴露给外部调用的跳转方法
     */
    fun jump(context: Context?, uriString: String) {
        if (context !is Activity) {
            throw IllegalStateException("当前上下文不适用于进行页面路由跳转")
        }
        // 解析 Uri 字符串并根据协议跳转到不同类型的 Activity
        val uri = Uri.parse(uriString) ?: return
        when (uri.scheme) {
            "native" -> startActivityWithNativeUri(context, uri)
            "http", "https" -> startActivityWithUrl(context, uri)
            "app" -> startActivityWithAllInOneUri(context, uri)
            else -> throw IllegalStateException("无法匹配对应的 Uri Scheme")
        }
    }

    private fun startActivityWithAllInOneUri(activity: Activity, uri: Uri) {
        // TODO: 预留方法
    }

    private fun startActivityWithUrl(activity: Activity, uri: Uri) {
        // TODO: 预留方法
    }

    /**
     * 通过 Native Uri 匹配对应的 Activity
     */
    private fun startActivityWithNativeUri(activity: Activity, uri: Uri) {
        val mapping = map.getOrDefault(uri.host, defaultMapping)
        logd(tag = "Router", msg = "$mapping")

        if (mapping.mustLogin && BaseLogic.getLocalUser() != null) {
            startActivityWithNativeUri(activity, Uri.parse(URI_AUTH))
            return
        }

        // 匹配参数键值对
        val args = Bundle()
        mapping.paramKeys?.forEach { key ->
            val value = uri.getQueryParameter(key)
            if (value.checkNotEmpty()) {
                args.putString(key, value)
            }
        }

        execStartActivity(activity, mapping.target, args, mapping.requestCode)
    }

    private fun execStartActivity(activity: Activity,
                                  target: Class<*>,
                                  args: Bundle,
                                  requestCode: Int?) {
        val intent = Intent(activity, target)
        intent.putExtras(args)

        if (requestCode == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Map
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 默认跳转路由失败页
     */
    private val defaultMapping = Mapping(target = PageNotFoundActivity::class.java)

    private val map: Map<String, Mapping> = ArrayMap<String, Mapping>().apply {
        put("main", Mapping(target = MainActivity::class.java))
        put("auth", Mapping(target = AuthActivity::class.java))
    }

    ///////////////////////////////////////////////////////////////////////////
    // URI 常量 (便于定位是从哪些 Activity 跳转的)
    ///////////////////////////////////////////////////////////////////////////

    // 主页
    val URI_MAIN = "native://main"
    // 登录注册页
    val URI_AUTH = "native://auth"
    // 故事创作, 编辑页
    val URI_CREATE_STORY = "native://writing"
    // 故事详情页
    val URI_STORY_DETAIL = "native://story_detail"
}