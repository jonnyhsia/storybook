package com.jonnyhsia.storybook.kit

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.jonnyhsia.storybook.app.App

/**
 * UI 工具集
 */
object UIKit {

    fun sp2px(sp: Int): Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), App.INSTANCE.resources.displayMetrics)

    fun dp2px(dp: Int): Float = dp2px(dp.toFloat())

    fun dp2px(dp: Float): Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, App.INSTANCE.resources.displayMetrics)

    fun tintDrawable(drawable: Drawable, colors: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
        DrawableCompat.setTint(wrappedDrawable, colors)
        return wrappedDrawable
    }
}

fun Drawable.tint(@ColorInt tintColor: Int): Drawable? {
    val wrappedDrawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(wrappedDrawable, tintColor)
    return wrappedDrawable
}

/**
 * 隐藏键盘
 */
fun Activity.hideKeyboard() {
    val focusedView = currentFocus as? EditText ?: return
    focusedView.hideKeyboard()
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    if (imm != null && imm.isActive) {
        imm.hideSoftInputFromWindow(applicationWindowToken, 0)
    }
}