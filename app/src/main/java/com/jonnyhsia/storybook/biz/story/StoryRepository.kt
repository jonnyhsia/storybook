package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.biz.base.BaseRepository

class StoryRepository(val remoteDataSource: StoryRemoteDataSource,
                      val localDataSource: StoryLocalDataSource) : BaseRepository, StoryDataSource {

    override fun preload() {

    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance: StoryRepository = StoryRepository(
                StoryRemoteDataSource(), StoryLocalDataSource())
    }
}