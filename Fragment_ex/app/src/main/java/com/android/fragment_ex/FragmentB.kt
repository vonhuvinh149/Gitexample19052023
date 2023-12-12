package com.android.fragment_ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class FragmentB : Fragment() {

    private var btnB: Button? = null
    private var edtB: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_b, container, false)

        btnB = view.findViewById(R.id.btn_b)
        edtB = view.findViewById(R.id.edt_b)

        btnB?.setOnClickListener {
            val tvA = activity?.findViewById(R.id.txt_a) as TextView
            tvA.text = edtB?.text
        }

        return view
    }
}