package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.app.AppError
import com.jonnyhsia.storybook.biz.base.BaseLogic
import com.jonnyhsia.storybook.biz.base.OnFailed
import com.jonnyhsia.storybook.biz.base.OnSubscribe
import com.jonnyhsia.storybook.biz.profile.entity.User

class ProfileLocalDataSource : BaseLogic(), ProfileDataSource {

    override fun getMyProfile(onSubscribe: OnSubscribe,
                              onSuccess: GetProfileSuccess,
                              onNoLoginUserFound: OnNoLoginUserFound,
                              onFailed: OnFailed) {

        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

    override fun getUserProfile(userId: String, onSuccess: GetProfileSuccess, onFailed: OnFailed) {

    }

    fun saveProfile(user: User) {
        // TODO: 保存到 SharedPreference 中
    }
}