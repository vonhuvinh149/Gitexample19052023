package com.android.bottom_navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        replaceFragment(ShopFragment())
        event()
    }

    private fun event() {
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_shop -> {
                    replaceFragment(ShopFragment())
                    true
                }

                R.id.navigation_cart -> {
                    replaceFragment(CartFragment())
                    true
                }

                R.id.navigation_gift -> {
                    replaceFragment(GiftsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

    private fun initView() {
        bottomNavigation = findViewById(R.id.bottom_navigation)
    }
}