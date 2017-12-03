package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.biz.base.BaseLogic
import com.jonnyhsia.storybook.biz.base.OnFailed
import com.jonnyhsia.storybook.biz.base.OnSubscribe
import com.jonnyhsia.storybook.http.RxHttpHandler
import com.jonnyhsia.storybook.http.RxHttpSchedulers

class ProfileRemoteDataSource : BaseLogic(), ProfileDataSource {

    val api = retrofit.create(ProfileApi::class.java)

    override fun getMyProfile(onSubscribe: OnSubscribe,
                              onSuccess: GetProfileSuccess,
                              onNoLoginUserFound: OnNoLoginUserFound,
                              onFailed: OnFailed) {
        val user = getLocalUser()
        if (user == null) {
            onNoLoginUserFound()
            return
        }
        api.getUserProfile(user.username)
                .compose(RxHttpSchedulers.composeSingle())
                .compose(RxHttpHandler.handleSingle())
                .doOnSubscribe(onSubscribe)
                .doOnSuccess(onSuccess)
                .doOnError {
                    onFailed(1, it.message)
                }
    }

    override fun getUserProfile(userId: String, onSuccess: GetProfileSuccess, onFailed: OnFailed) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}