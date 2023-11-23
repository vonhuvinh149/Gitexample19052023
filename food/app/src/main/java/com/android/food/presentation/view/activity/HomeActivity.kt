package com.android.food.presentation.view.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.food.R
import com.android.food.common.AppSharePreference
import com.android.food.presentation.view.fragment.HistoryFragment
import com.android.food.presentation.view.fragment.ProductFragment
import com.android.food.presentation.view.fragment.ProfileFragment
import com.android.food.utils.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private val sharePreference = AppSharePreference(this)
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        event()

    }

    private fun initView() {
        bottomNavigation = findViewById(R.id.bottom_navigation_menu)
        replaceFragment(ProductFragment())
    }

    private fun event() {
        setOnItemFragment()
    }

    private fun setOnItemFragment() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    replaceFragment(ProductFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.item_history -> {
                    if (sharePreference.isTokenValid()) {
                        if (sharePreference.isTokenExpirationTime()) {
                            replaceFragment(HistoryFragment())
                        } else {
                            ToastUtils.showToast(
                                this,
                                "Phiên làm việc hết hạn , vui lòng đăng nhập lại!"
                            )
                            runBlocking {
                                delay(1000)
                                val intent =
                                    Intent(this@HomeActivity, TokenRefreshActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        ToastUtils.showToast(this@HomeActivity, "vui lòng đăng nhập")
                        val intent = Intent(this@HomeActivity, SignInActivity::class.java)
                        startActivity(intent)
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_profile -> {
                    replaceFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            onBackDialog()
        }

        this.doubleBackToExitPressedOnce = true

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 1000)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_fragment, fragment)
            .commit()
    }

    private fun onBackDialog() {
        val dialog: Dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_dialog_custom)

        val btnCancel: TextView = dialog.findViewById(R.id.btn_cancel)
        val btnAgree: TextView = dialog.findViewById(R.id.btn_agree)

        btnCancel.setOnClickListener {
            dialog.cancel()
        }

        btnAgree.setOnClickListener {
            finish()
        }

        dialog.show()
    }

}