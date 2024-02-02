package com.android.tablayout

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager_test)
        tabLayout = findViewById(R.id.tab_test)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        tabLayout.setTabIconTint(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_home)
                }
                1 -> tab.text = "tab 2"
                2 -> tab.text = "tab 3"
                3 -> tab.text = "tab 4"
            }
        }.attach()
    }
}