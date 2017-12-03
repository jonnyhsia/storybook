package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.biz.base.Response
import com.jonnyhsia.storybook.biz.profile.entity.User
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileApi {

    @POST("/profile")
    fun getUserProfile(@Query("user_id") userId: String)
            : Single<Response<User>>
}