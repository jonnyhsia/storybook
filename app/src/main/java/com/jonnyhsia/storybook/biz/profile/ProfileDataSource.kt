package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.biz.base.BaseDataSource
import com.jonnyhsia.storybook.biz.base.OnFailed
import com.jonnyhsia.storybook.biz.base.OnSubscribe
import com.jonnyhsia.storybook.biz.profile.entity.User

typealias GetProfileSuccess = (user: User) -> Unit
typealias OnNoLoginUserFound = () -> Unit

interface ProfileDataSource : BaseDataSource {

    /**
     * 获取当前用户的个人档案
     *
     * @param onNoLoginUserFound 用户未登录的回调
     */
    fun getMyProfile(onSubscribe: OnSubscribe,
                     onSuccess: GetProfileSuccess,
                     onNoLoginUserFound: OnNoLoginUserFound,
                     onFailed: OnFailed)

    /**
     * 获取用户的个人档案
     *
     * @param userId 用户ID, 即唯一用户名
     */
    fun getUserProfile(userId: String,
                       onSuccess: GetProfileSuccess,
                       onFailed: OnFailed)

}