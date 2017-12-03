package com.jonnyhsia.storybook.biz.profile.entity

/**
 * 用户的基本信息
 */
data class User(
        val username: String,
        val nickname: String,
        val email: String? = null,
        val avatar: String? = null,
        val secret_key: String? = null,
        val userOverview: UserOverview? = null) {

    /**
     * 用户数据概览
     */
    data class UserOverview(
            val todayStory: Int,
            val totalStory: Int,
            val publicStory: Int,
            val friends: Int)
}