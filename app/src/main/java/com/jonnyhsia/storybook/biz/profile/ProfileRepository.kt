package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.biz.base.BaseRepository
import com.jonnyhsia.storybook.biz.base.CacheWrapper
import com.jonnyhsia.storybook.biz.base.OnFailed
import com.jonnyhsia.storybook.biz.base.OnSubscribe
import com.jonnyhsia.storybook.biz.profile.entity.User

class ProfileRepository(private val remoteDataSource: ProfileRemoteDataSource,
                        private val localDataSource: ProfileLocalDataSource)
    : BaseRepository, ProfileDataSource {

    private var cachedProfileData = CacheWrapper<User>()

    override fun preload() {
        // 仅预加载
        this.getMyProfile(
                onSubscribe = {},
                onSuccess = {},
                onNoLoginUserFound = {},
                onFailed = { _, _ -> })
    }

    override fun getMyProfile(onSubscribe: OnSubscribe,
                              onSuccess: GetProfileSuccess,
                              onNoLoginUserFound: OnNoLoginUserFound,
                              onFailed: OnFailed) {
        if (cachedProfileData.isValid) {
            onSuccess(cachedProfileData.unpack())
        }

        remoteDataSource.getMyProfile(
                onSubscribe = onSubscribe,
                onSuccess = { user ->
                    // 回调前保存缓存与本地
                    cachedProfileData.cache(user)
                    localDataSource.saveProfile(user)
                    onSuccess(user)
                },
                onNoLoginUserFound = onNoLoginUserFound,
                onFailed = onFailed)
    }

    override fun getUserProfile(userId: String, onSuccess: GetProfileSuccess, onFailed: OnFailed) {

    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance: ProfileRepository = ProfileRepository(
                ProfileRemoteDataSource(), ProfileLocalDataSource())
    }
}