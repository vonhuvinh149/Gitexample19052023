package com.android.food.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.food.R
import com.android.food.common.AppSharePreference
import com.android.food.presentation.view.fragment.HistoryFragment
import com.android.food.presentation.view.fragment.ProductFragment
import com.android.food.presentation.view.fragment.ProfileFragment
import com.android.food.utils.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        token = AppSharePreference(this@HomeActivity).getUser()?.token ?: ""

        initView()
        event()

    }

    private fun initView() {
        bottomNavigation = findViewById(R.id.bottom_navigation_menu)
        replaceFragment(ProductFragment(this))
    }

    private fun event() {
        setOnItemFragment()
    }

    private fun setOnItemFragment() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    replaceFragment(ProductFragment(this))
                    return@setOnItemSelectedListener true
                }

                R.id.item_history -> {
                    if (token.isNotBlank()) {
                        replaceFragment(HistoryFragment(this))
                    } else {
                        ToastUtils.showToast(this@HomeActivity, "vui lòng đăng nhập")
                        val intent = Intent(this@HomeActivity, SignInActivity::class.java)
                        startActivity(intent)
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_profile -> {
                    replaceFragment(ProfileFragment(this))
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_fragment, fragment)
            .commit()
    }

}