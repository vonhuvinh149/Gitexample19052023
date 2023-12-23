package com.android.firebase_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var btnRegister: Button? = null
    private var btnSignIn: Button? = null
    private var saveImg: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        event()

    }

    private fun event() {
        btnRegister?.setOnClickListener {
            val email = edtEmail?.text.toString()
            val password = edtPassword?.text.toString()
            register(email, password)
        }

        btnSignIn?.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        saveImg?.setOnClickListener {
            val intent = Intent(this, SaveImageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        auth = Firebase.auth
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnRegister = findViewById(R.id.btn_register)
        btnSignIn = findViewById(R.id.btn_sign_in)
        saveImg = findViewById(R.id.save_image)
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "register successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "register failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
