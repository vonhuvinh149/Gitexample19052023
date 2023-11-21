package com.android.food.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.food.R
import com.android.food.common.AppResource
import com.android.food.utils.validation.Validator
import com.android.food.presentation.viewmodel.SignUpViewModel
import com.android.food.utils.ToastUtils
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var signUpViewModel: SignUpViewModel
    private var edtEmail: TextInputLayout? = null
    private var edtPassword: TextInputLayout? = null
    private var edtName: TextInputLayout? = null
    private var edtPhone: TextInputLayout? = null
    private var edtAddress: TextInputLayout? = null
    private lateinit var toolBar: Toolbar
    private var btnRegister: TextView? = null
    private var layoutLoading: LinearLayout? = null
    private val validator: Validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpViewModel(this@RegisterActivity) as T
            }
        })[SignUpViewModel::class.java]

        initView()
        observer()
        eventRegister()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun observer() {
        signUpViewModel.getLoadingLiveData().observe(this) {
            layoutLoading?.isGone = !it
        }

        signUpViewModel.getUserLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    ToastUtils.showToast(this, "Register Success")
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message.toString())
                }

                else -> {
                    ToastUtils.showToast(this@RegisterActivity, it.message ?: "")
                }
            }
        }
    }

    private fun eventRegister() {
        btnRegister?.setOnClickListener {
            val email = edtEmail?.editText?.text.toString()
            val password = edtPassword?.editText?.text.toString()
            val name = edtName?.editText?.text.toString()
            val phone = edtPhone?.editText?.text.toString()
            val address = edtAddress?.editText?.text.toString()
            checkValidator(email, name, password, phone, address)
        }
    }

    private fun checkValidator(
        email: String,
        name: String,
        password: String,
        phone: String,
        address: String
    ) {
        val msgEmail = validator.isValidateEmail(email)
        val msgName = validator.isValidateName(name)
        val msgPassword = validator.isValidatePassword(password)
        val msgPhone = validator.isValidatePhone(phone)
        val msgAddress = validator.isValidateAddress(address)
        if (msgEmail.isEmpty() && msgName.isEmpty() && msgPassword.isEmpty() && msgPhone.isEmpty() && msgAddress.isEmpty()) {
            signUpViewModel.executeSignUp(email, name, password, phone, address)
        } else {
            edtEmail?.error = msgEmail
            edtName?.error = msgName
            edtPassword?.error = msgPassword
            edtPhone?.error = msgPhone
            edtAddress?.error = msgAddress
        }
    }



    private fun customToolBar() {
        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        edtEmail = findViewById(R.id.edit_email_input)
        edtPassword = findViewById(R.id.edit_password_input)
        edtName = findViewById(R.id.edit_name_input)
        edtPhone = findViewById(R.id.edit_phone_input)
        edtAddress = findViewById(R.id.edit_address_input)
        btnRegister = findViewById(R.id.btn_save_signup)
        layoutLoading = findViewById(R.id.layout_loading_sign_up)
        customToolBar()
    }
}