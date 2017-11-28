package com.jonnyhsia.storybook.page.main

import android.os.Bundle
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.base.DayNightActivity

class MainActivity : DayNightActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
