package com.jonnyhsia.storybook.biz

import android.content.Context
import com.jonnyhsia.storybook.biz.story.StoryRepository

object Injection {

    private var isInitialized = false

    /**
     * 初始化
     */
    fun initialize(context: Context) {
        if (isInitialized) {
            throw IllegalStateException("Injection 已经完成初始化")
        }
        BaseRepository.initialize(context)
        isInitialized = true
    }

    /**
     * 预加载数据
     * 在这里调用各个 Repository 的 preload
     */
    fun preload() {

    }

    ///////////////////////////////////////////////////////////////////////////
    // 暴露各个 Repository
    ///////////////////////////////////////////////////////////////////////////

    fun getStoryRepository() = StoryRepository.instance()
}