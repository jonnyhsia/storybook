package com.jonnyhsia.storybook.page.base

interface BasePresenter {

    /**
     * 第一次加载
     */
    fun start()

    /**
     * 每次加载
     */
    fun resume()

    /**
     * 销毁
     */
    fun destroy()

}