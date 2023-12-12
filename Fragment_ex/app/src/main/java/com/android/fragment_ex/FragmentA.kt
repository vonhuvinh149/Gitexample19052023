package com.android.fragment_ex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentA : Fragment() {

    private var btnA: Button? = null
    private var tvA: TextView? = null
    private var edtA: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_a, container, false)

        btnA = view.findViewById(R.id.btn_a)
        tvA = view.findViewById(R.id.txt_a)
        edtA = view.findViewById(R.id.edt_a)

        btnA?.setOnClickListener {
            val textView: TextView = activity?.findViewById(R.id.textView) as TextView
            textView.text  = edtA?.text
        }

        return view
    }

    fun bindingContent(str: String) {
        Log.d("BBB", str)
        tvA?.text = str
    }
}