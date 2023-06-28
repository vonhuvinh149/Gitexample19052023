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
    private lateinit var addition: Button;
    private lateinit var subtraction: Button;
    private lateinit var multiplication: Button
    private lateinit var division: Button
    private lateinit var textView: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number1 = findViewById(R.id.nb1);
        number2 = findViewById(R.id.nb2);
        addition = findViewById(R.id.button)
        subtraction = findViewById(R.id.button2)
        multiplication = findViewById(R.id.button3)
        division = findViewById(R.id.button4)
        textView = findViewById(R.id.textView);
        var result: Int;

        addition.setOnClickListener {
            val nb1 = number1.text.toString()
            val nb2 = number2.text.toString()
            if (nb1.isEmpty() || nb2.isEmpty()) {
                Toast.makeText(this@MainActivity, "vui long nhap", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            result = nb1.toInt() + nb2.toInt()
            textView.text = result.toString()
        }

        subtraction.setOnClickListener {
            val nb1 = number1.text.toString()
            val nb2 = number2.text.toString()
            if (nb1.isEmpty() || nb2.isEmpty()) {
                Toast.makeText(this@MainActivity, "vui long nhap", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            result = nb1.toInt() - nb2.toInt()
            textView.text = result.toString()
        }

        multiplication.setOnClickListener {
            val nb1 = number1.text.toString()
            val nb2 = number2.text.toString()
            if (nb1.isEmpty() || nb2.isEmpty()) {
                Toast.makeText(this@MainActivity, "vui long nhap", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            result = nb1.toInt() * nb2.toInt()
            textView.text = result.toString()
        }

        division.setOnClickListener {
            val nb1 = number1.text.toString()
            val nb2 = number2.text.toString()
            if (nb1.isEmpty() || nb2.isEmpty()) {
                Toast.makeText(this@MainActivity, "vui long nhap", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            result = nb1.toInt() / nb2.toInt()
            textView.text = result.toString()
        }
    }
}