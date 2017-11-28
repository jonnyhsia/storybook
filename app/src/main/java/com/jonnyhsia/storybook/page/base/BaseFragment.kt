package com.jonnyhsia.storybook.page.base

import android.support.v4.app.Fragment
import com.jonnyhsia.storybook.router.Router

/**
 * Fragment 基类
 */
abstract class BaseFragment : Fragment() {

    fun jump(uri: String) {
        Router.jump(activity, uri)
    }
}