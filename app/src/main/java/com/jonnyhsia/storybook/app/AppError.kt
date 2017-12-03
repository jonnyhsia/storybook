package com.jonnyhsia.storybook.app

/**
 * Created by JonnyHsia on 17/11/9.
 * 应用内异常枚举
 * TODO 换个好名字
 */
enum class AppError(val errorCode: Int,
                    val exception: Exception) {

    // Android 异常
    CONTEXT_UNSUITABLE(999, Exception("当前上下文不适用于跳转页面")),
    INCORRECT_FRAGMENT_POS(998, Exception("错误的 Fragment Position")),

    // 网络请求异常
    REQUEST_FAILED(1, Exception("请求失败")),
    REQUEST_FAILED_UNEXPECTED(2, Exception("网络异常")),

    // 登录注册, 用户相关异常
    NO_USER_LOGIN_INFO(101, Exception("本地没有保存的用户登录信息")),

    PASSWORD_NOT_MATCH_USERNAME(102, Exception("用户名与密码不匹配")),
    // 请求异常
    INCORRECT_REMOTE_REQUEST(151, Exception("分发错误的远程请求")),
    INCORRECT_LOCAL_REQUEST(152, Exception("分发错误的本地请求")),

    // 路由异常
    ROUTER_INVALID_PAGE_SCHEME(200, Exception("非法的页面")),

    ROUTER_MISMATCHED_SCHEME(201, Exception("页面协议不匹配"));

    companion object {

        /**
         * 通过 ErrorCode 来查找对应的 AppError
         *
         * @param errorCode 错误代码
         */
        @Suppress("MemberVisibilityCanPrivate")
        fun codeOf(errorCode: Int): AppError? {
            return values().firstOrNull { it.errorCode == errorCode }
        }

        fun codeOfException(errorCode: Int): Exception? = codeOf(errorCode)?.exception
    }

    class AppException(message: String?,
                       throwable: Throwable? = null) : Exception(message, throwable)
}