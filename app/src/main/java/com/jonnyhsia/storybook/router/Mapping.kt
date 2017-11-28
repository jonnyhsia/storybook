package com.jonnyhsia.storybook.router

/**
 * 跳转目标 Mapping
 *
 * @param schema        跳转的协议
 * @param allInOneUri   统一跳转标识 (预留字段)
 * @param target        跳转的目标 (Web 页面统一 WebViewActivity)
 * @param paramKeys     参数的键名集合
 * @param requestCode   请求的 Code
 * @param mustLogin     是否需要登录
 */
@Suppress("MemberVisibilityCanPrivate")
data class Mapping(val schema: String = "native",
                   val allInOneUri: String? = null,
                   val target: Class<*>,
                   val paramKeys: List<String>? = null,
                   val requestCode: Int? = null,
                   val mustLogin: Boolean = false)