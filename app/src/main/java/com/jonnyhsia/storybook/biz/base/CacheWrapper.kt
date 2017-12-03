package com.jonnyhsia.storybook.biz.base

class CacheWrapper<T> {

    private var cachedData: T? = null

    var isValid = false
        private set

    /**
     * 取出缓存数据
     */
    fun unpack(): T {
        val unpackData = cachedData
        if (!isValid || unpackData == null) {
            throw IllegalStateException("务必检查代码, 缓存已失效或无缓存数据")
        }
        return unpackData
    }

    /**
     * 存入缓存数据
     */
    fun cache(data: T) {
        isValid = true
        cachedData = data
    }

    /**
     * 缓存数据标记为无效
     */
    fun invalidate() {
        cachedData = null
        isValid = false
    }
}