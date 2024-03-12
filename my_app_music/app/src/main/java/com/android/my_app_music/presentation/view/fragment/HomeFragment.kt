package com.android.my_app_music.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.android.my_app_music.R


class HomeFragment : Fragment() {
    private lateinit var view: View
    private var btn: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

}