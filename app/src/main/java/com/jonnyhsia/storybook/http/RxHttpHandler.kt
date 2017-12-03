package com.jonnyhsia.storybook.http

import com.jonnyhsia.storybook.app.AppError
import com.jonnyhsia.storybook.biz.base.Response
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.functions.Function

/**
 * Created by JonnyHsia on 17/10/25.
 * 封装对 Api 请求的统一处理
 */
object RxHttpHandler {

    /**
     * 对 Observable 类型的观察者的处理
     */
    fun <T> handleObservable(checkNull: Boolean = true): ObservableTransformer<Response<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap(Function<Response<T>, ObservableSource<T>> { response ->
                if (response.code != 0) {
                    return@Function Observable.error(AppError.REQUEST_FAILED.exception)
                }
                if (checkNull && response.data == null) {
                    return@Function Observable.error(AppError.REQUEST_FAILED_UNEXPECTED.exception)
                }
                Observable.create { e ->
                    response.data?.let { e.onNext(it) }
                }
            })
        }
    }

    /**
     * 对 Single 类型的观察者的处理
     */
    fun <T> handleSingle(checkNull: Boolean = true): SingleTransformer<Response<T>, T> {
        return SingleTransformer { upstream ->
            // 通过 flatMap 将 Response<T> 类型的 Single 转换成 T 类型 SingleSource
            upstream.flatMap(Function<Response<T>, SingleSource<T>> { response ->
                // 处理 response 的各个状态，返回各类的错误到下游
                if (response.code != 0) {
                    return@Function Single.error(AppError.REQUEST_FAILED.exception)
                }
                if (checkNull && response.data == null) {
                    return@Function Single.error(AppError.REQUEST_FAILED_UNEXPECTED.exception)
                }
                // 若无误，则创建一个 Single 传递到下游
                response.data?.let { Single.just(it) }
            })
        }
    }

}