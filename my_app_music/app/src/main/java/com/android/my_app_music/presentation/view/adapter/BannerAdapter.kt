package com.android.my_app_music.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Advertisement
import com.squareup.picasso.Picasso

class BannerAdapter(
    private val context: Context,
    private val lists: MutableList<Advertisement>
) : PagerAdapter() {

    override fun getCount(): Int {
        return lists.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.custom_banner, null)

        val imgBackgroundBanner: ImageView = view.findViewById(R.id.img_background_banner)
        val imgBanner: ImageView = view.findViewById(R.id.img_banner)
        val tvTitle: TextView = view.findViewById(R.id.tv_title_banner)
        val tvContent: TextView = view.findViewById(R.id.tv_content)

        Picasso.get().load(lists[position].image).into(imgBackgroundBanner)
        Picasso.get().load(lists[position].image).into(imgBanner)
        tvContent.text = lists[position].content
        tvTitle.text = lists[position].title

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}