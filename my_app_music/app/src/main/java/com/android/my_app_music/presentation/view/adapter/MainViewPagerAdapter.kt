package com.android.my_app_music.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.my_app_music.utils.OnClickItem


class MainViewPagerAdapter(frag: FragmentManager) : FragmentPagerAdapter(frag) {

    private var arrFrag: ArrayList<Fragment> = arrayListOf()
    private var arrTitle: ArrayList<String> = arrayListOf()

    override fun getCount(): Int {
        return arrFrag.size
    }

    override fun getItem(position: Int): Fragment {
        return arrFrag[position]
    }

    fun addFragment(frag: Fragment, title: String) {
        arrFrag.add(frag)
        arrTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return arrTitle[position]
    }


}