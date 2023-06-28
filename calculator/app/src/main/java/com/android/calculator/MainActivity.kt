package com.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var number1: EditText;
    private lateinit var number2: EditText;
    private lateinit var operatorUp: Button;
    private lateinit var operatorDown: Button;
    private lateinit var textView: TextView ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number1 = findViewById(R.id.nb1);
        number2 = findViewById(R.id.nb2);
        operatorUp = findViewById(R.id.button)
        operatorDown = findViewById(R.id.button2)
        textView= findViewById(R.id.textView);
        var result: Int;

        operatorUp.setOnClickListener{
            if (number1.length() > 0  && number2.length() > 0){
                result = number1.getText().toString().toInt() + number2.getText().toString().toInt()
                textView.text = result.toString();
            }else{
                Toast.makeText(this@MainActivity , "vui long nhap" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}