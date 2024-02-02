package com.android.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GirlAdapter : RecyclerView.Adapter<GirlAdapter.GirlViewHolder>() {

    private var girls: MutableList<Girl> = mutableListOf()

    fun setData(list: List<Girl>) {
        if (girls.size > 0) {
            girls.clear()
        }
        girls.addAll(list)
        notifyDataSetChanged()
    }

    inner class GirlViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var img: ImageView = view.findViewById(R.id.img_girl)
        private var tvTitle: TextView = view.findViewById(R.id.tv_title)

        fun onBind(girl: Girl) {
            img.setImageResource(girl.resourceId)
            tvTitle.text = girl.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_girl , parent , false)
        return GirlViewHolder(view)
    }

    override fun getItemCount() = girls.size

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
        holder.onBind(girls[position])
    }
}