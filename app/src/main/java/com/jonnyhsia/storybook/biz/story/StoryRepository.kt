package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.biz.BaseRepository

class StoryRepository(val remoteDataSource: StoryRemoteDataSource,
                      val localDataSource: StoryLocalDataSource) : BaseRepository(), StoryDataSource {

    private val storyApi = retrofit.create(StoryApi::class.java)

    override fun preload() {

    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance: StoryRepository = StoryRepository(StoryRemoteDataSource(), StoryLocalDataSource())

    }
}