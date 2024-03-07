package com.android.my_app_music.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerPlaySongAdapter(frag: FragmentManager) : FragmentPagerAdapter(frag) {

    private var arrFrag: ArrayList<Fragment> = arrayListOf()

    override fun getCount(): Int {
        return arrFrag.size
    }

    override fun getItem(position: Int): Fragment {
        return arrFrag[position]
    }

    fun addFragment(frag : Fragment){
        arrFrag.add(frag)
    }
}