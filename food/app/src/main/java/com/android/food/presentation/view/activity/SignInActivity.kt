package com.android.food.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.food.R
import com.android.food.common.AppResource
import com.android.food.presentation.viewmodel.SignInViewModel
import com.android.food.utils.SpannedUtils
import com.android.food.utils.ToastUtils
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SignInActivity : AppCompatActivity() {

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var ediTextEmail: EditText
    private lateinit var ediTextPassword: EditText
    private lateinit var buttonSignIn: TextView
    private lateinit var tvRegister: TextView
    private lateinit var layoutLoading: LinearLayout
    private val myRef = Firebase.database.getReference("image")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignInViewModel(this@SignInActivity) as T
            }
        })[SignInViewModel::class.java]

        initView()
        observerData()
        event()
    }

    private fun observerData() {

        signInViewModel.getLoading().observe(this) {
            layoutLoading.isGone = !it
        }

        signInViewModel.getUserLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    ToastUtils.showToast(this, "Login success!!!")
//                    setFirebase(it.data?.email.toString())
                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is AppResource.Error -> ToastUtils.showToast(this, it.message.toString())
            }
        }
    }

    private fun setFirebase(email : String ){
        myRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChild(email)){
                    myRef.child(email).setValue("https://s.net.vn/EN5L")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Failed to read value.", error.toException())
            }

        })
    }

    private fun event() {
        buttonSignIn.setOnClickListener {
            val email = ediTextEmail.text.toString()
            val password = ediTextPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this, "Input invalid!!!")
                return@setOnClickListener
            }
            signInViewModel.executeSignIn(email, password, this)
        }
        displayTextViewRegister()
    }

    private fun displayTextViewRegister() {
        SpannableStringBuilder().apply {
            append("Don't have an account?")
            append(
                SpannedUtils.setClickColorLink(
                    text = "Register",
                    context = this@SignInActivity,
                    onListenClick = onClickRegister
                )
            )
            tvRegister.text = this
            tvRegister.highlightColor = Color.TRANSPARENT
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun initView() {
        ediTextEmail = findViewById(R.id.text_edit_email)
        ediTextPassword = findViewById(R.id.text_edit_password)
        buttonSignIn = findViewById(R.id.button_sign_in)
        tvRegister = findViewById(R.id.text_view_register)
        layoutLoading = findViewById(R.id.layout_loading)
    }

    private var onClickRegister = {
        val intent = Intent(this@SignInActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}