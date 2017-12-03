package com.jonnyhsia.storybook.biz.base

import io.reactivex.disposables.Disposable

// 通用的无入参成功回调
typealias OnSuccess = () -> Unit

// 通用的返回数据为空回调
typealias OnEmpty = () -> Unit

// 通用的失败回调
typealias OnFailed = (error: Int, errorMessage: String?) -> Unit

// 通用的异常回调
typealias OnError = (error: Throwable) -> Unit

// 订阅
typealias OnSubscribe = (d: Disposable) -> Unit