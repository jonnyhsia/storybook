package com.jonnyhsia.storybook.biz

import android.content.Context
import com.jonnyhsia.storybook.biz.base.BaseLogic
import com.jonnyhsia.storybook.biz.profile.ProfileRepository
import com.jonnyhsia.storybook.biz.story.StoryRepository

object Injection {

    /**
     * 初始化
     */
    fun initialize(context: Context) {
        if (BaseLogic.isInitialized) {
            throw IllegalStateException("Base Logic 已经完成初始化")
        }
        BaseLogic.initialize(context)
    }

    /**
     * 预加载数据
     * 在这里调用各个 Repository 的 preload
     */
    fun preload() {
        getProfileRepository().preload()
    }

    ///////////////////////////////////////////////////////////////////////////
    // 暴露各个 Repository
    ///////////////////////////////////////////////////////////////////////////

    fun getStoryRepository() = StoryRepository.instance()

    fun getProfileRepository() = ProfileRepository.instance()
}