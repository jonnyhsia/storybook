package com.jonnyhsia.uilib.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.jonnyhsia.uilib.R
import kotlin.properties.Delegates

/**
 * Created by JonnyHsia on 17/8/2.
 * 自定义底部导航
 */
@Suppress("unused")
class BottomNavigation : LinearLayout {

    constructor(context: Context) : super(context) {
        initAttrs(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    // 是否启用主按钮
    private var primaryEnable = false
    // 主按钮图标资源
    private var primaryIconRes = 0
    // 主按钮文字
    private var primaryText: String? = null
    private var primaryNavItem: BottomNavItem? = null

    // 点击事件
    private var onItemSelectListener: ((oldPos: Int, pos: Int, obj: BottomNavItem) -> Unit)? = null
    private var onItemReselectListener: ((pos: Int, obj: BottomNavItem) -> Unit)? = null
    private var onPrimarySelectListener: (() -> Unit)? = null

    private var navItems = ArrayList<BottomNavItem>()
    private var navViews = ArrayList<ViewGroup>()

    var activeIndex by Delegates.observable(0, { _, oldValue, newValue ->
        updateAppearance(oldValue, newValue)
    })

    @Suppress("DEPRECATION")
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigation)
        primaryEnable = typedArray.getBoolean(R.styleable.BottomNavigation_primaryEnable, false)
        if (primaryEnable) {
            primaryIconRes = typedArray.getResourceId(R.styleable.BottomNavigation_primaryIcon, 0)
            primaryText = typedArray.getString(R.styleable.BottomNavigation_primaryText)
            primaryNavItem = BottomNavItem(primaryText, primaryIconRes, true)
        }
        typedArray.recycle()
    }

    /**
     * Add item select listener
     */
    fun addItemSelectListener(listener: (oldPos: Int, pos: Int, obj: BottomNavItem) -> Unit): BottomNavigation {
        onItemSelectListener = listener
        return this
    }

    /**
     * Add item select listener
     */
    fun addItemReselectListener(listener: (pos: Int, obj: BottomNavItem) -> Unit): BottomNavigation {
        onItemReselectListener = listener
        return this
    }

    /**
     * Add item select listener
     */
    fun addPrimarySelectListener(listener: () -> Unit): BottomNavigation {
        onPrimarySelectListener = listener
        return this
    }


    /**
     * When PrimaryMode is enabled
     * It would be better if the size of items is even
     * @param items collection of the navigation items
     * @return itself
     */
    fun setNavItems(items: List<BottomNavItem>): BottomNavigation {
        if (items.size < 3) {
            Log.d("BottomNavigation", "Bottom navigation items shouldn't less than three.")
            return this
        }
        // Clear current collection before setup
        navItems.clear()
        navItems.addAll(items)
        // 判断是否开启了 PrimaryMode
        if (primaryEnable) {
            navItems.add(navItems.size / 2, primaryNavItem!!)
        }
        bindItemSelectListener()

        return this
    }

    /**
     * Bind select listener to all nav items
     */
    private fun bindItemSelectListener() {
        navViews.clear()
        for (pos in 0 until navItems.size) {
            val item = navItems[pos]
            LayoutInflater.from(context)
                .inflate(if (item.isPrimary) R.layout.view_nav_primary else R.layout.view_nav_item, this, true)
            val view = getChildAt(pos)

            // 给对应 pos 的 NavView 设置图标和点击事件
            view.findViewById<ImageView>(R.id.ic_nav)?.setImageResource(item.res)
            view.setOnClickListener {
                if (item.isPrimary) {
                    onPrimarySelectListener?.invoke()
                } else {
                    // 判断是否是重复点击
                    if (pos == activeIndex) {
                        onItemReselectListener?.invoke(pos, item)
                    } else {
                        onItemSelectListener?.invoke(activeIndex, pos, item)
                        activeIndex = pos
                    }
                }
            }
            navViews.add(view as ViewGroup)
        }
        initNavAppearance()
    }

    private fun initNavAppearance() {
        // 遍历除了 PrimaryItem 以外的 NavItem
        for (i in 0 until navViews.size) {
            val view = navViews[i]
            if (navItems[i].isPrimary) {
                continue
            }
            // 遍历 NavItem 的 ChildView
            // 设置为对应的不透明度 1f (选中) 或 0.38f (未选中)
            for (n in 0 until view.childCount) {
                view.getChildAt(n).alpha = if (i == activeIndex) 1f else 0.38f
            }
        }
    }

    /**
     * 点击对应 Pos 的 Item
     */
    public fun performClickItem(pos: Int) {
        navViews.getOrNull(pos)?.performClick()
    }

    private fun updateAppearance(oldIndex: Int, newIndex: Int) {
        val oldV = navViews[oldIndex]
        val newV = navViews[newIndex]

        for (n in 0 until oldV.childCount) {
            oldV.getChildAt(n).alpha = 0.38f
            newV.getChildAt(n).alpha = 1f
        }
    }

    data class BottomNavItem(var title: String?,
                             var res: Int,
                             var isPrimary: Boolean = false)
}
