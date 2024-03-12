package com.android.my_app_music.presentation.view.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.android.my_app_music.R
import com.android.my_app_music.presentation.view.adapter.MainViewPagerAdapter
import com.android.my_app_music.presentation.view.fragment.HomeFragment
import com.android.my_app_music.presentation.view.fragment.SearchFragment
import com.android.my_app_music.presentation.viewmodel.AdvertisementViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private lateinit var viewPager: ViewPager
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()

    }

    private fun initView() {
        tabLayout = findViewById(R.id.myTabLayout)
        viewPager = findViewById(R.id.myViewPager)
        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mainViewPagerAdapter.addFragment(HomeFragment())
        mainViewPagerAdapter.addFragment(SearchFragment())
        viewPager.adapter = mainViewPagerAdapter
        tabLayout?.setupWithViewPager(viewPager)
        tabLayout?.getTabAt(0)?.setIcon(R.drawable.ic_home)
        tabLayout?.getTabAt(1)?.setIcon(R.drawable.ic_search)
    }
}