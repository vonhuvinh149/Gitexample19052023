package com.android.optionmenu

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    val homeFragment = HomeFragment()
    val searchFragment = SearchFragment()
    val profileFragment = ProfileFragment()
    var navigationView : NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.nav_left_menu)

        navigationView?.itemIconTintList = null


        navigationView?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mnu_home -> {
                    Log.d("BBB" , "1")
                    binding(homeFragment)
                }

                R.id.mnu_search -> {
                    Log.d("BBB" , "2")
                    binding(searchFragment)
                }

                R.id.mnu_user -> {
                    Log.d("BBB" , "3")
                    binding(profileFragment)
                }
            }
            true
        }

        binding(homeFragment)

    }

    private fun binding(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}