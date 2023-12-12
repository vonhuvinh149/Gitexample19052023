package com.android.fragment_ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentC : Fragment() {

    private var txtC: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_c, container, false)

        txtC = view.findViewById(R.id.tv_c)

        val bundle = arguments

        if (bundle != null ){
            txtC?.text = bundle.getString("name")
        }

        return view
    }
}