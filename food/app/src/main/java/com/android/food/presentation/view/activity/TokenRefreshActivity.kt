package com.android.food.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.food.R
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
import com.android.food.presentation.viewmodel.SignInViewModel
import com.android.food.utils.ToastUtils

class TokenRefreshActivity : AppCompatActivity() {

    private var btnLogin: TextView? = null
    private var edtPassword: EditText? = null
    private var btnToSignIn: TextView? = null
    private var layoutLoading: LinearLayout? = null
    private var tvName: TextView? = null
    private var sharePreference = AppSharePreference(this)

    private lateinit var signInViewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_refresh)

        signInViewModel = ViewModelProvider(this@TokenRefreshActivity,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SignInViewModel(this@TokenRefreshActivity) as T
                }
            })[SignInViewModel::class.java]

        initView()
        observerData()
        event()

    }

    private fun event() {
        btnToSignIn?.setOnClickListener {
            val intent = Intent(this@TokenRefreshActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        btnLogin?.setOnClickListener {
            val password: String = edtPassword?.text.toString()
            val email: String = sharePreference.getUser()?.email ?: ""
            if (password.isEmpty()) {
                return@setOnClickListener
            }
            signInViewModel.executeTokenRefresh(password, email)
        }
    }

    private fun initView() {
        btnToSignIn = findViewById(R.id.btn_to_login)
        btnLogin = findViewById(R.id.button_refresh)
        edtPassword = findViewById(R.id.text_edit_password)
        layoutLoading = findViewById(R.id.layout_loading)
        tvName = findViewById(R.id.tv_name_user)
        tvName?.text = sharePreference.getUser()?.name
    }

    private fun observerData() {

        signInViewModel.getLoading().observe(this) {
            layoutLoading?.isGone = !it
        }

        signInViewModel.getTokenRefreshLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    Toast.makeText(this, "Login success !", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@TokenRefreshActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this@TokenRefreshActivity, it.message ?: "")
                }
            }
        }


    }
}