package com.jonnyhsia.storybook.biz.base

import android.content.Context
import android.util.Log
import com.jonnyhsia.storybook.BuildConfig
import com.jonnyhsia.storybook.app.Const
import com.jonnyhsia.storybook.biz.profile.entity.User
import com.jonnyhsia.storybook.kit.AlgorithmKit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

open class BaseLogic {

    companion object {
        private val READ_TIMEOUT = 10L
        private val CONNECT_TIMEOUT = 10L
        private val BASE_URL = Const.BASE_URL

        @JvmStatic
        var isInitialized = false

        private val httpInterceptor = { chain: Interceptor.Chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val httpUrlBuilder = originalHttpUrl.newBuilder()

            val randomString = AlgorithmKit.randomString(10)
            val signToken = AlgorithmKit.hmac256(Const.APP_NAME + randomString, Const.SIGN_SECRET_KEY)
                    ?: throw IllegalStateException("SignToken 不应为空")

            if (getLocalUser() != null) {
                // TODO: 请求默认添加用户数据
                httpUrlBuilder.addQueryParameter("user_id", "")
                        .addQueryParameter("token", "")
                        .addQueryParameter("secret_key", "")
            }

            val request = original.newBuilder()
                    .addHeader("mark", "android/storybook/" + BuildConfig.VERSION_NAME)
                    .addHeader("random", randomString)
                    .addHeader("sign_token", signToken)
                    .url(httpUrlBuilder.build())
                    .build()
            chain.proceed(request)
        }

        var context: WeakReference<Context> by Delegates.notNull()

        var retrofit: Retrofit by Delegates.notNull()

        fun initialize(initialContext: Context) {
            context = WeakReference(initialContext)

            val clientBuilder = OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(httpInterceptor)


            if (BuildConfig.DEBUG) {
                clientBuilder.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Log.d("HttpLog", it)
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
            }

            retrofit = Retrofit.Builder()
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            isInitialized = true
        }

        fun getLocalUser(): User? {
            return null
        }
    }
}