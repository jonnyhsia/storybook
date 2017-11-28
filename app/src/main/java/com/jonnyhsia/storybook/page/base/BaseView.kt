package com.jonnyhsia.storybook.page.base

/**
 * View 基接口
 */
interface BaseView<in P : BasePresenter> {

    /**
     * 页面路由跳转
     */
    fun jump(uri: String)

    /**
     * 绑定 Presenter
     */
    fun bindPresenter(presenter: P)
}