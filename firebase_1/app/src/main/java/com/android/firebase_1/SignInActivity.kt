package com.android.firebase_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singn_in)

        initView()
        event()
    }

    private fun event() {
        btnLogin?.setOnClickListener {
            val email = edtEmail?.text.toString()
            val password = edtPassword?.text.toString()
            signIn(email, password)
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Authentication Successfully.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    this,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun initView() {
        auth = Firebase.auth
        edtEmail = findViewById(R.id.edt_email_login)
        edtPassword = findViewById(R.id.edt_password_login)
        btnLogin = findViewById(R.id.btn_login)
    }
}