package com.android.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.databinding.databinding.FagmentMyBinding

class MyFragment : Fragment() {

    private lateinit var fragmentMyBinding: FagmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        fragmentMyBinding = FagmentMyBinding.inflate(layoutInflater , container , false)

        val myFragmentViewModel = MyFragmentViewModel("Data binding in fragment")

        fragmentMyBinding.myFragmentViewModel = myFragmentViewModel

        return fragmentMyBinding.root

    }
}